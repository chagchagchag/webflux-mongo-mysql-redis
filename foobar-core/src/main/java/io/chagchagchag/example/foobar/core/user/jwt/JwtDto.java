package io.chagchagchag.example.foobar.core.user.jwt;

import java.util.Date;

public record JwtDto(
    String id, String email, String username, Date expiration
){
}