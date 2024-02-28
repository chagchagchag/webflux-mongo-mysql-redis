package io.chagchagchag.example.data_reactive.usecase.image.factory;

import io.chagchagchag.example.data_reactive.usecase.image.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageFactory {
  public Image emptyImage(){
    return new Image("", "", "");
  }

}
