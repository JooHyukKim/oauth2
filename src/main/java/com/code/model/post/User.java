package com.code.model.post;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
  @Id
  @Column(name = "user_id", nullable = false)
  private Long id;

  @Column(name = "username", nullable = false)
  private String username;
}
