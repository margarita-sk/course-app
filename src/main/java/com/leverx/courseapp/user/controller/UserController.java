package com.leverx.courseapp.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Api(value = "users")
@ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 401, message = "Unauthorized"),
      @ApiResponse(code = 500, message = "Internal Server Error")
    })
@RequestMapping(path = "/users", produces = "application/json")
public class UserController {

  @GetMapping("/profile")
  public Map<String, Object> userDetails(JwtAuthenticationToken authentication) {
    var profile = authentication.getTokenAttributes();
    return profile;
  }
}
