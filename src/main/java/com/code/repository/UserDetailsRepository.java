package com.code.repository;

import com.code.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  List<User> findFirstByEmail(String email);
}
