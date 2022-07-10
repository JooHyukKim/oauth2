package com.code.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Slf4j
@Controller
public class LoginController {

  @Autowired
  private TokenEndpoint tokenEndpoint;

  @RequestMapping(value = "/oauth/login", method = RequestMethod.GET)
  public String login(@RequestParam(value = "error", required = false) String error, Model model) {
    model.addAttribute("error", error);
    return "login";
  }

  // 코드 발행 커스텀
  @RequestMapping("/oauth/token")
  public ResponseEntity<OAuth2AccessToken> oauthToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
    ResponseEntity<OAuth2AccessToken> response = tokenEndpoint.postAccessToken(principal, parameters);
    String grantType = StringUtils.defaultString(parameters.get("grant_type"), "");

    // 토큰 발행 방식에 따른 비즈니스 코드 작성
    if (StringUtils.equals("authorization_code", grantType)) {

    } else if (StringUtils.equals("refresh_token", grantType)) {

    } else if (StringUtils.equals("password", grantType)) {

    } else if (StringUtils.equals("client_credentials", grantType)) {

    }
    log.info("## get token => {} | {}", response.getBody().getAdditionalInformation(), grantType);
    return response;
  }
}
