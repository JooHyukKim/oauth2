package com.code.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

  @Email
  private String email;

  @NotEmpty
  private String username;

  @NotEmpty
  private String password;

  @NotEmpty
  private String passwordCheck;

  public boolean validPasswordsMatch() {
    return password.equals(passwordCheck);
  }
}
