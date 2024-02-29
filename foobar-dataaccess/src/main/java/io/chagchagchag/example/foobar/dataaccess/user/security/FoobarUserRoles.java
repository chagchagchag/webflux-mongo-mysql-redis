package io.chagchagchag.example.foobar.dataaccess.user.security;

import lombok.Getter;

@Getter
public enum FoobarUserRoles {
  ROLE_USER("ROLE_USER");
  private final String roleName;
  FoobarUserRoles(String roleName){
    this.roleName = roleName;
  }
}
