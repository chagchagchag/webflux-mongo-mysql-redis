package io.chagchagchag.example.foobar.core.user;

public record SignupUserRequest (
    String name,
    Integer age,
    String password,
    String profileImageId
){

}
