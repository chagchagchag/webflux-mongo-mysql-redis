package io.chagchagchag.example.foobar.user.config;

import io.chagchagchag.example.foobar.core.user.User;
import io.chagchagchag.example.foobar.core.user.security.JwtDto;
import io.chagchagchag.example.foobar.dataaccess.user.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class JwtProcessor {
  public String generateToken(Key key, CustomUserDetails userDetails){
    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setExpiration(new Date(System.currentTimeMillis() + 864000000))
        .claim("id", userDetails.getUser().getId())
//        .claim("email", userDetails.getUser().())
        .claim("profileImageId", userDetails.getUser().getProfileImageId())
        .claim("username", userDetails.getUser().getName())
        .claim("password", userDetails.getPassword())
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateToken(Key key, User user){
    return Jwts.builder()
        .setSubject(user.name())
        .setExpiration(new Date(System.currentTimeMillis() + 864000000))
        .claim("id", user.id())
//        .claim("email", userDetails.getUser().())
        .claim("profileImageId", user.profileImage().map(image -> image.id()).orElse(""))
        .claim("username", user.name())
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public void addJwtAtResponseHeader(String jwt, ServerHttpResponse response){
    response.getHeaders().add("Authorization", String.format("Bearer %s", jwt));
  }

  public JwtDto degenerateToken(Key key, String token){
    JwtParser parser = Jwts.parserBuilder()
        .setSigningKey(key)
        .build();

    Jws<Claims> claimsJws = parser.parseClaimsJws(token);

    return new JwtDto(
        claimsJws.getBody().get("id", String.class),
        claimsJws.getBody().get("profileImageId", String.class),
        claimsJws.getBody().get("username", String.class),
//        claimsJws.getBody().get("password", String.class),
        claimsJws.getBody().getExpiration()
    );
  }

  public Boolean checkContainsBearer(String header){
    var len = "Bearer ".length();
    return header.substring(0, len).equalsIgnoreCase("Bearer");
  }

  // 만료되면 true
  // 만료되지 않았으면 false
  public Boolean checkIfExpired(Date expiration){
    return expiration.after(new Date());
  }
}