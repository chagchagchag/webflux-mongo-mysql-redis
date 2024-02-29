package io.chagchagchag.example.foobar.user.usecase.image.factory;

import io.chagchagchag.example.foobar.user.usecase.image.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageFactory {
  public Image emptyImage(){
    return new Image("", "", "");
  }

}
