package io.chagchagchag.example.foobar.core.user;

import io.chagchagchag.example.foobar.core.image.ProfileImageResponse;
import io.chagchagchag.example.foobar.core.user.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

  public UserResponse toUserResponse(UserProfile userProfile){
    return new UserResponse(
        userProfile.id(), userProfile.name(), userProfile.age(), userProfile.followCnt(),
        userProfile.profileImage().map(
            image -> new ProfileImageResponse(
                image.id(),
                image.name(),
                image.url()
            )
        )
    );
  }

}
