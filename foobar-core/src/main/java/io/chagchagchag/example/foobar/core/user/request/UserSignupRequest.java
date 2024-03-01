package io.chagchagchag.example.foobar.core.user.request;

public record UserSignupRequest(
    String name,
    Integer age,
    String password
){

}
