package io.chagchagchag.example.foobar.core.image;

import org.springframework.stereotype.Component;

@Component
public class ImageFactory {
  public Image emptyImage(){
    return new Image("", "", "");
  }

}
