package com.code.configuration.database;

import com.code.domain.oauth.OAuthUser;
import com.code.domain.oauth.OAuthUserRepository;
import com.code.domain.post.PostUser;
import com.code.domain.post.PostUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MultipleDatabaseConfigTest {

  @Autowired
  private PostUserRepository postUserRepository;


  @Autowired
  private OAuthUserRepository oAuthUserRepository;

  @Test
  void OAuthUser와_PostUser_동시_생성테스트() {
    // given
    OAuthUser oAuthUser = createOAuthUser();
    assertDoesNotExist(oAuthUserRepository.findByUsername(oAuthUser.getUsername()));
    assertDoesNotExist(postUserRepository.findByUsername(oAuthUser.getUsername()));

    // when
    OAuthUser savedOAuthUser = oAuthUserRepository.save(oAuthUser);
    PostUser postUser = savedOAuthUser.toPostUser();
    PostUser savedPostUser = postUserRepository.save(postUser);

    // then
    assertThat(oAuthUser).isEqualTo(savedOAuthUser);
    assertThat(postUser.getUsername()).isEqualTo(savedPostUser.getUsername());
    assertThat(postUser.getId()).isEqualTo(savedPostUser.getId());
  }

  private void assertDoesNotExist(Optional<?> optionalUser) {
    assertThrows(NoSuchUserException.class, () -> {
      optionalUser.orElseThrow(NoSuchUserException::new);
    });
  }

  private OAuthUser createOAuthUser() {
    OAuthUser user = new OAuthUser();
    String uniqueUsername = "user" + System.currentTimeMillis();
    user.setNickname(uniqueUsername);
    user.setEmail(uniqueUsername + "@gmail.com");
    user.setPassword("1234");
    user.setState(OAuthUser.DEFAULT_STATE);
    user.setUsername(uniqueUsername);
    return user;
  }

  class NoSuchUserException extends RuntimeException {
  }

}
