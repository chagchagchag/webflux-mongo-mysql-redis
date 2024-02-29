package io.chagchagchag.example.foobar.dataaccess.user.entity.factory;

import io.chagchagchag.example.foobar.dataaccess.user.entity.UserEntity;
import io.chagchagchag.example.foobar.dataaccess.user.security.FoobarUserRoles;
import org.springframework.stereotype.Component;

@Component
public class UserEntityFactory {
  public UserEntity ofCreateRoleUser(String name, Integer age, String password, String profileImageId){
    FoobarUserRoles roleUser = FoobarUserRoles.ROLE_USER;
    return UserEntity.createBuilder()
        .age(age)
        .name(name)
        .password(password)
        .profileImageId(profileImageId)
        .roles(roleUser.getRoleName())
        .build();
  }
}
