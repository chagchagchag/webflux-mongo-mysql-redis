package io.chagchagchag.example.foobar.user.jwt;

import io.chagchagchag.example.foobar.core.user.jwt.JwtDto;
import io.chagchagchag.example.foobar.user.usecase.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JwtSupportTest {
  @DisplayName("JWT_생성_분해_테스트")
  @Test
  public void TEST_JWT_생성_분해_테스트(){
    // given
    String username = "John Deer";
    String userId = "jdman";
    Key key = SecurityProperties.key;

    // when
    // JWT 생성
    String jwt = Jwts.builder()
      .setSubject(username)
      .setExpiration(new Date(System.currentTimeMillis() + 864000000))
      .claim("id", userId)
      .claim("username", username)
      .signWith(key, SignatureAlgorithm.HS256)
      .compact();

    // then
    // JWT 분해
    JwtParser parser = Jwts.parserBuilder()
        .setSigningKey(key)
        .build();

    Jws<Claims> claimsJws = parser.parseClaimsJws(jwt);

    assert claimsJws.getBody().get("id", String.class).equals(userId);
    assert claimsJws.getBody().get("username", String.class).equals(username);
  }
  
}
