package com.code.service;

import com.code.controller.request.JoinRequest;
import com.code.repository.UserRepository;
import com.code.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;

  public void joinNewUser(JoinRequest joinRequest) {
    User user = User.makeFrom(joinRequest);
    userRepository.save(user);
  }

  public boolean emailNotUsed(String email) {
    return userRepository.findFirstByEmail(email).isEmpty();
  }
}
