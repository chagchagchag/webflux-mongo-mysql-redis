package io.chagchagchag.example.foobar.user.application;

import io.chagchagchag.example.foobar.core.user.SignupUserRequest;
import io.chagchagchag.example.foobar.core.user.UserMapper;
import io.chagchagchag.example.foobar.core.user.UserResponse;
import io.chagchagchag.example.foobar.user.usecase.UserDefaultUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

  private final UserDefaultUseCase userDefaultUseCase;
  private final UserMapper userMapper;

  @GetMapping("/{userId}")
  public Mono<UserResponse> getUserById(
      @PathVariable String userId
  ){
    return ReactiveSecurityContextHolder
        .getContext()
        .flatMap(securityContext -> {
          String name = securityContext.getAuthentication().getName();

          if(!name.equals(userId)){
            return Mono.error(
                new ResponseStatusException(HttpStatus.UNAUTHORIZED)
            );
          }

          return userDefaultUseCase.findByUserId(userId)
              .map(user -> userMapper.toUserResponse(user))
              .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
        });
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/signup")
  public Mono<UserResponse> signupUser(
      @RequestBody SignupUserRequest signupUserRequest,
      ServerHttpResponse response
  ){
    return userDefaultUseCase
        .createUser(
            signupUserRequest.name(), signupUserRequest.age(), signupUserRequest.password(),
            signupUserRequest.profileImageId(), response
        )
        .map(user -> userMapper.toUserResponse(user));
  }

}
