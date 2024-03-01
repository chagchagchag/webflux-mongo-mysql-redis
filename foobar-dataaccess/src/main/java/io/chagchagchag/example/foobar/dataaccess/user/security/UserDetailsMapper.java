package io.chagchagchag.example.foobar.dataaccess.user.security;

import io.chagchagchag.example.foobar.dataaccess.user.entity.UserEntity;
import java.util.ArrayList;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {
  public User defaultUserDetails(UserEntity userEntity){
    return new User(
        String.valueOf(userEntity.getId()),
        userEntity.getPassword(),
        true, true, true, true,
        new ArrayList<GrantedAuthority>()
    );
  }
}
