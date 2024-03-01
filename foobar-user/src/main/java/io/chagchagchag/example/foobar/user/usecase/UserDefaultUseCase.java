package io.chagchagchag.example.foobar.user.usecase;

import io.chagchagchag.example.foobar.core.image.ImageFactory;
import io.chagchagchag.example.foobar.core.image.ImageMapper;
import io.chagchagchag.example.foobar.core.image.ImageResponse;
import io.chagchagchag.example.foobar.core.user.request.SignupUserRequest;
import io.chagchagchag.example.foobar.core.user.User;
import io.chagchagchag.example.foobar.dataaccess.user.entity.factory.UserEntityFactory;
import io.chagchagchag.example.foobar.dataaccess.user.repository.UserR2dbcRepository;
import io.chagchagchag.example.foobar.dataaccess.user.valueobject.UserEntityMapper;
import io.chagchagchag.example.foobar.core.user.jwt.JwtProcessor;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserDefaultUseCase {
  private final JwtProcessor jwtProcessor;
//  private final PasswordEncoder bcryptPasswordEncoder;
  private final UserR2dbcRepository userR2dbcRepository;
  private final UserEntityFactory userEntityFactory;
  private final UserEntityMapper userEntityMapper;

  private final ImageFactory imageFactory;
  private final ImageMapper imageMapper;

  private final WebClient imageServerWebClient;

  public Mono<User> findByUserId(String userId){
    return userR2dbcRepository.findById(Long.parseLong(userId))
        .flatMap(userEntity -> {
          String imageId = userEntity.getProfileImageId();
          Map<String, String> uriVariableMap = Map.of("imageId", imageId);

          return imageServerWebClient.get()
              .uri("/api/images/{imageId}", uriVariableMap)
              .retrieve()
              .toEntity(ImageResponse.class)
              .map(r -> r.getBody())
              .map(imageMapper::fromImageResponse)
              .switchIfEmpty(Mono.just(imageFactory.emptyImage()))
              .map(image -> userEntityMapper.toUser(userEntity, Optional.of(imageFactory.emptyImage())));
        });
  }

  @Transactional
  public Mono<User> createUser(
      SignupUserRequest request, ServerHttpResponse response
  ){
    var newUserEntity = userEntityFactory
        .ofCreateRoleUser(request.name(), request.age(), request.password(), null);

    var emptyImage = imageFactory.emptyImage();

    return userR2dbcRepository.save(newUserEntity)
        .map(userEntity -> userEntityMapper.toUser(userEntity, Optional.of(emptyImage)))
        .map(user -> {
          String token = jwtProcessor.generateToken(SecurityProperties.key, user);
          jwtProcessor.addJwtAtResponseHeader(token, response);
          return user;
        });
  }
}
