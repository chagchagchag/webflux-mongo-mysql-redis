package io.chagchagchag.example.foobar.user.config.security;

import io.chagchagchag.example.foobar.dataaccess.user.repository.UserR2dbcRepository;
import io.chagchagchag.example.foobar.dataaccess.user.security.UserDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements ReactiveUserDetailsService {
  private final UserR2dbcRepository userR2dbcRepository;
  private final UserDetailsMapper userDetailsMapper;

  // id 가 존재하는지만 검사하고, 이것을 UserDetails 로 반환한다.
  @Override
  public Mono<UserDetails> findByUsername(String userId) {
    return userR2dbcRepository
        // TODO:: id 를 email 로 바꿀 예정. 지금은 시간이 없어서 후퇴
        .findById(Long.parseLong(userId))
        .map(userEntity -> userDetailsMapper.defaultUserDetails(userEntity));
  }

}
