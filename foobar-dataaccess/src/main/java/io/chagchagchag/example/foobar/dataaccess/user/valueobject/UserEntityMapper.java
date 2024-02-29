package io.chagchagchag.example.foobar.dataaccess.user.valueobject;


import io.chagchagchag.example.foobar.core.image.Image;
import io.chagchagchag.example.foobar.core.user.User;
import io.chagchagchag.example.foobar.dataaccess.user.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

  public User toUser(UserEntity userEntity, Optional<Image> profileImage){
    return new User(
        String.valueOf(userEntity.getId()), userEntity.getName(), userEntity.getAge(),
        profileImage, List.of(), 0L
    );
  }

}
