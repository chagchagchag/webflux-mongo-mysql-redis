package io.chagchagchag.example.data_reactive.usecase.user.valueobject;

import io.chagchagchag.example.data_reactive.dataaccess.entity.UserEntity;
import io.chagchagchag.example.data_reactive.usecase.image.Image;
import io.chagchagchag.example.data_reactive.usecase.user.User;
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
