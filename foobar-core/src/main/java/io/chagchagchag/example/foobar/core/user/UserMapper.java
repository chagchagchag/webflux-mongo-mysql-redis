package io.chagchagchag.example.foobar.core.user;

import io.chagchagchag.example.foobar.core.image.ProfileImageResponse;
import io.chagchagchag.example.foobar.core.user.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserResponse toUserResponse(User user){
    return new UserResponse(
        user.id(), user.name(), user.age(), user.followCnt(),
        user.profileImage().map(
            image -> new ProfileImageResponse(
                image.id(),
                image.name(),
                image.url()
            )
        )
    );
  }

}
