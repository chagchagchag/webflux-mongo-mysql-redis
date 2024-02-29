package io.chagchagchag.example.foobar.user.usecase;

import io.chagchagchag.example.foobar.user.dataaccess.entity.factory.UserEntityFactory;
import io.chagchagchag.example.foobar.user.dataaccess.repository.UserR2dbcRepository;
import io.chagchagchag.example.foobar.user.usecase.image.factory.ImageFactory;
import io.chagchagchag.example.foobar.user.usecase.image.mapper.ImageMapper;
import io.chagchagchag.example.foobar.user.usecase.image.response.ImageResponse;
import io.chagchagchag.example.foobar.user.usecase.user.User;
import io.chagchagchag.example.foobar.user.usecase.user.valueobject.UserMapper;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserDefaultUseCase {
  private final UserR2dbcRepository userR2dbcRepository;
  private final UserEntityFactory userEntityFactory;
  private final UserMapper userMapper;

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
              .map(image -> userMapper.fromUserEntity(userEntity, Optional.of(imageFactory.emptyImage())));
        });
  }

  @Transactional
  public Mono<User> createUser(
      String name, Integer age, String password, String profileImageId
  ){
    var newUserEntity = userEntityFactory
        .ofCreateUser(name, age, password, profileImageId);

    var emptyImage = imageFactory.emptyImage();

    return userR2dbcRepository.save(newUserEntity)
        .map(userEntity -> userMapper.fromUserEntity(userEntity, Optional.of(emptyImage)));
  }
}
