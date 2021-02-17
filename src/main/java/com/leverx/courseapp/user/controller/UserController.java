package com.leverx.courseapp.user.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@AllArgsConstructor
@Api(value = "users")
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RequestMapping(path = "/users", produces = "application/json")
public class UserController {

    @RequestMapping("/authenticated")
    public Map<String, String> getAuthenticatedUser(@AuthenticationPrincipal OidcUser user) {
       var name = user.getFullName();
       var email = user.getEmail();
       var authorities = user.getAuthorities().toString();
       return Map.of("name", name, "email", email, "authorities", authorities);
    }

}
