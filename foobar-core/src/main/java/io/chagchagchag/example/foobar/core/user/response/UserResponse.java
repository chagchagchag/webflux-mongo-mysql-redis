package io.chagchagchag.example.foobar.core.user.response;

import io.chagchagchag.example.foobar.core.image.ProfileImageResponse;
import java.util.Optional;

public record UserResponse(
    String id,
    String name,
    int age,
    Long followCount,
    Optional<ProfileImageResponse> image
) {

}
