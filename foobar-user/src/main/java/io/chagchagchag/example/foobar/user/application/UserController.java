package io.chagchagchag.example.foobar.user.application;

import io.chagchagchag.example.foobar.core.user.request.UserLoginRequest;
import io.chagchagchag.example.foobar.core.user.request.UserSignupRequest;
import io.chagchagchag.example.foobar.core.user.UserProfileMapper;
import io.chagchagchag.example.foobar.core.user.response.UserLoginResponse;
import io.chagchagchag.example.foobar.core.user.response.UserResponse;
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
  private final UserProfileMapper userProfileMapper;

  @GetMapping("/profile/{userId}")
  public Mono<UserResponse> getUserById(
      @PathVariable String userId
  ){
    log.info(ReactiveSecurityContextHolder.getContext().toString());
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
              .map(user -> userProfileMapper.toUserResponse(user))
              .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
        })
        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/signup")
  public Mono<UserResponse> signupUser(
      @RequestBody UserSignupRequest userSignupRequest,
      ServerHttpResponse response
  ){
    return userDefaultUseCase
        .createUser(userSignupRequest, response)
        .map(user -> userProfileMapper.toUserResponse(user));
  }

  @PostMapping("/login")
  public Mono<UserLoginResponse> login(
      @RequestBody UserLoginRequest userLoginRequest,
      ServerHttpResponse response
  ){
    return userDefaultUseCase
        .login(userLoginRequest, response);
  }

}
