package io.chagchagchag.example.foobar.user.security;

import io.chagchagchag.example.foobar.dataaccess.user.repository.UserR2dbcRepository;
import io.chagchagchag.example.foobar.dataaccess.user.security.CustomUserDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService extends MapReactiveUserDetailsService {
  private final UserR2dbcRepository userR2dbcRepository;
  private final CustomUserDetailsMapper customUserDetailsMapper;

  // id 가 존재하는지만 검사하고, 이것을 UserDetails 로 반환한다.
  @Override
  public Mono<UserDetails> findByUsername(String userId) {
    return userR2dbcRepository
        .findById(Long.parseLong(userId))
        .map(userEntity -> customUserDetailsMapper.defaultUserDetails(userEntity));
  }

}
