package com.code.service;

import com.code.domain.oauth.OAuthUserRepository;
import com.code.domain.oauth.OAuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

  @Autowired
  private OAuthUserRepository OAuthUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    OAuthUser OAuthUserInfo = OAuthUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such username : " + username));
    return OAuthUserInfo;
  }

}
