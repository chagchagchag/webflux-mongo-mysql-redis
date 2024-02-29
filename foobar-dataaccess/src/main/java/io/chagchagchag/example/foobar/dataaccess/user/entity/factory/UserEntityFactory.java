package io.chagchagchag.example.foobar.dataaccess.user.entity.factory;

import io.chagchagchag.example.foobar.dataaccess.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityFactory {
  public UserEntity ofCreateUser(String name, Integer age, String password, String profileImageId){
    return UserEntity.createBuilder()
        .age(age)
        .name(name)
        .password(password)
        .profileImageId(profileImageId)
        .build();
  }
}