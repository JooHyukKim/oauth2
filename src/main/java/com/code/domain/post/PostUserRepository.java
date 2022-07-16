package com.code.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostUserRepository extends JpaRepository<PostUser, Long> {
  Optional<PostUser> findByUsername(String username);
}
