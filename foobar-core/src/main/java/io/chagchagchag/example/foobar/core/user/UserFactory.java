package io.chagchagchag.example.foobar.core.user;

import io.chagchagchag.example.foobar.core.article.Article;
import io.chagchagchag.example.foobar.core.image.Image;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
  public UserProfile defaultUserProfile(
      String id, String name, int age,
      Optional<Image> profileImage,
      List<Article> articleList,
      Long followCnt
  ){
    return new UserProfile(
        id, name, age,
        profileImage, articleList, followCnt
    );
  }
}
