package io.chagchagchag.example.foobar.user.usecase.user.valueobject;

import io.chagchagchag.example.foobar.user.dataaccess.entity.UserEntity;
import io.chagchagchag.example.foobar.user.usecase.image.Image;
import io.chagchagchag.example.foobar.user.usecase.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public User fromUserEntity(UserEntity userEntity, Optional<Image> profileImage){
    return new User(
        String.valueOf(userEntity.getId()), userEntity.getName(), userEntity.getAge(),
        profileImage, List.of(), 0L
    );
  }

}
