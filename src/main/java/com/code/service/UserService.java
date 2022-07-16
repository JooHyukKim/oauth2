package com.code.service;

import com.code.controller.request.JoinRequest;
import com.code.repository.UserDetailsRepository;
import com.code.repository.UsernameRepository;
import com.code.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserDetailsRepository userDetailsRepository;
  private final UsernameRepository usernameRepository;

  public void joinNewUser(JoinRequest joinRequest) {
    User user = User.makeFrom(joinRequest);

    userDetailsRepository.save(user);
  }

  public boolean emailNotUsed(String email) {
    return userDetailsRepository.findFirstByEmail(email).isEmpty();
  }
}
