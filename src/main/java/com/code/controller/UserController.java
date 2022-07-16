package com.code.controller;

import com.code.controller.request.JoinRequest;
import com.code.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("join")
  public ResponseEntity<Object> join(@RequestBody @Validated JoinRequest joinRequest) {
    userService.joinNewUser(joinRequest);
    return ResponseEntity.ok().body(null);
  }


  @GetMapping("email-check")
  public ResponseEntity<Object> emailCheck(@RequestParam("email") String email) {
    boolean emailNotUsed = userService.emailNotUsed(email);
    return emailNotUsed ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
  }
}
