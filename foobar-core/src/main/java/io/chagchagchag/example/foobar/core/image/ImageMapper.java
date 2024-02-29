package io.chagchagchag.example.foobar.core.image;

import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

  public Image fromImageResponse(ImageResponse imageResponse){
    return new Image(
        imageResponse.id(), imageResponse.name(), imageResponse.url()
    );
  }
}
