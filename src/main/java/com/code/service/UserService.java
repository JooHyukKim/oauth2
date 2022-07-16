package com.code.service;

import com.code.controller.request.JoinRequest;
import com.code.domain.oauth.OAuthUserRepository;
import com.code.domain.oauth.OAuthUser;
import com.code.domain.post.PostUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
  private final OAuthUserRepository oauthUserRepository;
  private final PostUserRepository postUserRepository;

  public void joinNewUser(JoinRequest joinRequest) {
    OAuthUser oauthUser = OAuthUser.makeFrom(joinRequest);
    OAuthUser savedOauthUser = oauthUserRepository.save(oauthUser);
    postUserRepository.save(savedOauthUser.toPostUser());
  }

  public boolean emailNotUsed(String email) {
    return oauthUserRepository.findFirstByEmail(email).isEmpty();
  }
}
