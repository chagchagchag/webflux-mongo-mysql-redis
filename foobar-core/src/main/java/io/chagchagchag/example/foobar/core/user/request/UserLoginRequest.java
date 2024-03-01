package io.chagchagchag.example.foobar.core.user.request;

public record UserLoginRequest (
    String userId,
    String password
){
}
