package io.chagchagchag.example.foobar.user.usecase.image.mapper;

import io.chagchagchag.example.foobar.user.usecase.image.Image;
import io.chagchagchag.example.foobar.user.usecase.image.response.ImageResponse;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

  public Image fromImageResponse(ImageResponse imageResponse){
    return new Image(
        imageResponse.id(), imageResponse.name(), imageResponse.url()
    );
  }
}
