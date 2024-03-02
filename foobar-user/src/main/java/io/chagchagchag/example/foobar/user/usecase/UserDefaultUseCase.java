package io.chagchagchag.example.foobar.user.usecase;

import io.chagchagchag.example.foobar.core.image.ImageFactory;
import io.chagchagchag.example.foobar.core.image.ImageMapper;
import io.chagchagchag.example.foobar.core.image.ImageResponse;
import io.chagchagchag.example.foobar.core.user.UserProfile;
import io.chagchagchag.example.foobar.core.user.jwt.JwtSupport;
import io.chagchagchag.example.foobar.core.user.request.UserLoginRequest;
import io.chagchagchag.example.foobar.core.user.request.UserSignupRequest;
import io.chagchagchag.example.foobar.core.user.response.UserLoginResponse;
import io.chagchagchag.example.foobar.dataaccess.user.entity.UserEntity;
import io.chagchagchag.example.foobar.dataaccess.user.entity.factory.UserEntityFactory;
import io.chagchagchag.example.foobar.dataaccess.user.repository.UserR2dbcRepository;
import io.chagchagchag.example.foobar.dataaccess.user.valueobject.UserEntityMapper;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserDefaultUseCase {
  private final JwtSupport jwtSupport;
  private final PasswordEncoder bcryptPasswordEncoder;
  private final UserR2dbcRepository userR2dbcRepository;
  private final UserEntityFactory userEntityFactory;
  private final UserEntityMapper userEntityMapper;

  private final ImageFactory imageFactory;
  private final ImageMapper imageMapper;

  private final WebClient imageServerWebClient;

  public Mono<UserProfile> findByUserId(String userId){
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
  public Mono<UserProfile> createUser(
      UserSignupRequest request, ServerHttpResponse response
  ){
    var encryptedPassword = bcryptPasswordEncoder.encode(request.password());
    var newUserEntity = userEntityFactory
        .ofCreateRoleUser(request.name(), request.age(), encryptedPassword, null);

    var emptyImage = imageFactory.emptyImage();

    return userR2dbcRepository.save(newUserEntity)
        .map(userEntity -> userEntityMapper.toUser(userEntity, Optional.of(emptyImage)))
        .map(user -> {
          String token = jwtSupport.generateToken(SecurityProperties.key, user.id(), user.name());
          jwtSupport.addJwtAtResponseHeader(token, response);
          return user;
        });
  }

  public Mono<UserLoginResponse> login(UserLoginRequest userLoginRequest, ServerHttpResponse response) {
    var requestToken = new UsernamePasswordAuthenticationToken(userLoginRequest.userId(), userLoginRequest.password());
    return findUser(requestToken)
        .map(userEntity -> {
          final String jwt = generateToken(userLoginRequest.userId(), userEntity.getName());
          jwtSupport.addJwtAtResponseHeader(jwt, response);
          return new UserLoginResponse("OK", Boolean.TRUE);
        });
  }

  public Mono<UserEntity> findUser(UsernamePasswordAuthenticationToken requestToken){
    return userR2dbcRepository
        .findById(Long.parseLong(requestToken.getName()))
        .map(userEntity -> {
          validatePassword(String.valueOf(requestToken.getCredentials()), userEntity.getPassword());
          return userEntity;
        });
  }

  public void validatePassword(String requestPassword, String userDetailsPassword){
    if(!bcryptPasswordEncoder.matches(requestPassword, userDetailsPassword)){
      throw new IllegalArgumentException("Password Error");
    }
  }

  public String generateToken(String userId, String username){
    return jwtSupport.generateToken(SecurityProperties.key, userId, username);
  }

}
