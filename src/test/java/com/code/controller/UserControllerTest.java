package com.code.controller;

import com.code.controller.request.JoinRequest;
import com.code.repository.UserDetailsRepository;
import com.code.vo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
  @Autowired
  private UserDetailsRepository userDetailsRepository;

  @Autowired
  private WebApplicationContext context;
  private MockMvc mockMvc;
  private static ObjectMapper mapper;

  @BeforeAll
  static void beforeAll() {
    mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
  }

  @BeforeEach
  void setUp() {
    mockMvc =
      MockMvcBuilders.webAppContextSetup(context)
        .alwaysDo(print())
        .apply(springSecurity())
        .build();
  }

  @Test
  @WithAnonymousUser
  void join() throws Exception {
    // given
    JoinRequest joinRequest = makeJoinRequest();
    assertUserNotExist(joinRequest);

    // when
    mockMvc.perform(
      post("/user/join")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(joinRequest))
    ).andExpect(status().isOk());

    // then
    assertUserExist(joinRequest);
  }

  private void assertUserNotExist(JoinRequest joinRequest) {
    assertThrows(UsernameNotFoundException.class, () ->
      userDetailsRepository.findByUsername(joinRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("..."))
    );
  }

  private void assertUserExist(JoinRequest joinRequest) {
    assertDoesNotThrow(() -> {
      User user = userDetailsRepository.findByUsername(joinRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("..."));
      assertThat(user.getEmail()).isEqualTo(joinRequest.getEmail());
      assertThat(user.getUsername()).isEqualTo(joinRequest.getUsername());
      assertThat(user.getPassword()).isEqualTo(joinRequest.getPassword());
    });
  }

  private JoinRequest makeJoinRequest() {
    JoinRequest request = new JoinRequest();
    String name = "user" + System.currentTimeMillis();
    request.setEmail(name + "@gmail.com");
    request.setUsername(name);
    request.setPassword(name + "1234");
    request.setPasswordCheck(name + "1234");
    return request;
  }


  @Test
  @WithAnonymousUser
  void emailCheck_securityBlock() throws Exception {
    // given
    User user = userDetailsRepository.save(User.makeFrom(makeJoinRequest()));

    // when
    mockMvc.perform(
      get("/user/email-check").queryParam("email", user.getEmail())
    ).andExpect(
      status().isUnauthorized());
  }

  @Test
  @WithMockUser
  void emailCheck_existing() throws Exception {
    // given
    User user = userDetailsRepository.save(User.makeFrom(makeJoinRequest()));

    // when
    mockMvc.perform(
      get("/user/email-check").queryParam("email", user.getEmail())
    ).andExpect(
      status().isNotAcceptable());
  }

  @Test
  @WithMockUser
  void emailCheck_canUse() throws Exception {
    // given
    JoinRequest request = makeJoinRequest();
    assertUserNotExist(request);

    // when
    mockMvc.perform(
      get("/user/email-check").queryParam("email", request.getEmail())
    ).andExpect(
      status().isOk());
  }
}