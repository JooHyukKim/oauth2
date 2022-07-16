package com.code.model.oauth;

import com.code.controller.request.JoinRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(length = 100)
  private String password;

  @Column(nullable = false, unique = true, length = 500)
  private String email;

  @Column(nullable = false, unique = true, length = 500)
  private String username;

  @Column(nullable = false, unique = true, length = 100)
  private String nickname;

  @Column(nullable = true, unique = false)
  private String state; // Y : 정상 회원 , L : 잠긴 계정, P : 패스워드 만료, A : 계정 만료
  private static final String DEFAULT_STATE = "Y";

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  // 계정 만료
  @Override
  public boolean isAccountNonExpired() {
    if (StringUtils.equalsIgnoreCase(state, "A")) {
      return false;
    }
    return true;
  }

  // 잠긴 계정
  @Override
  public boolean isAccountNonLocked() {
    if (StringUtils.equalsIgnoreCase(state, "L")) {
      return false;
    }
    return true;
  }

  // 패스워드 만료
  @Override
  public boolean isCredentialsNonExpired() {
    if (StringUtils.equalsIgnoreCase(state, "P")) {
      return false;
    }
    return true;
  }

  @Override
  public boolean isEnabled() {
    if (isCredentialsNonExpired() && isAccountNonExpired() && isAccountNonLocked()) {
      return true;
    }
    return false;
  }

  public static User makeFrom(JoinRequest joinRequest) {
    User user = new User();
    user.setUsername(joinRequest.getUsername());
    user.setPassword(joinRequest.getPassword());
    user.setEmail(joinRequest.getEmail());
    user.setState(DEFAULT_STATE);
    user.setNickname(joinRequest.getUsername());
    return user;
  }

}
