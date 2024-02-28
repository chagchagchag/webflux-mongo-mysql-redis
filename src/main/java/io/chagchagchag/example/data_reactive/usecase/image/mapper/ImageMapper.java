package io.chagchagchag.example.data_reactive.usecase.image.mapper;

import io.chagchagchag.example.data_reactive.usecase.image.Image;
import io.chagchagchag.example.data_reactive.usecase.image.response.ImageResponse;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

  public Image fromImageResponse(ImageResponse imageResponse){
    return new Image(
        imageResponse.id(), imageResponse.name(), imageResponse.url()
    );
  }
}
