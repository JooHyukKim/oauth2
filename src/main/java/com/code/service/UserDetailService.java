package com.code.service;

import com.code.repository.UserDetailsRepository;
import com.code.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

  @Autowired
  private UserDetailsRepository userDetailsRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User userInfo = userDetailsRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such username : " + username));
    return userInfo;
  }

}
