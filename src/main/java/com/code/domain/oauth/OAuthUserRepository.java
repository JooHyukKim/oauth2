package com.code.domain.oauth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OAuthUserRepository extends JpaRepository<OAuthUser, Long> {
  Optional<OAuthUser> findByUsername(String username);

  List<OAuthUser> findFirstByEmail(String email);
}
