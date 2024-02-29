package io.chagchagchag.example.foobar.core.user.request;

public record SignupUserRequest (
    String name,
    Integer age,
    String password,
    String profileImageId
){

}
