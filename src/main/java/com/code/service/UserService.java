package com.code.service;

import com.code.controller.request.JoinRequest;
import com.code.domain.oauth.OAuthUserRepository;
import com.code.domain.oauth.OAuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
  private final OAuthUserRepository oauthUserRepository;

  public void joinNewUser(JoinRequest joinRequest) {
    OAuthUser oauthUser = OAuthUser.makeFrom(joinRequest);

    oauthUserRepository.save(oauthUser);
  }

  public boolean emailNotUsed(String email) {
    return oauthUserRepository.findFirstByEmail(email).isEmpty();
  }
}
