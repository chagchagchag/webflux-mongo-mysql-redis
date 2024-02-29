package io.chagchagchag.example.foobar.user.usecase.user;

import io.chagchagchag.example.foobar.user.usecase.article.Article;
import io.chagchagchag.example.foobar.user.usecase.image.Image;
import java.util.List;
import java.util.Optional;

public record User (
    String id,
    String name,
    int age,
    Optional<Image> profileImage,
    List<Article> articleList,
    Long followCnt
){

}
