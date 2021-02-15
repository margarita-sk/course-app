package com.leverx.courseapp.user.controller;

import com.leverx.courseapp.security.util.JwToken;
import com.leverx.courseapp.security.util.JwtHelper;
import com.leverx.courseapp.security.util.UserDetailsServiceImpl;
import com.leverx.courseapp.user.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


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

    private final UserDetailsServiceImpl userService;
    private final JwtHelper jwtHelper;


    @PostMapping("/token")
    public JwToken authenticateUser(@RequestBody UserDto userDto) {
        var userDetails = userService.findByNameAndPassword(userDto.getUsername(), userDto.getPassword().toString());
        var token = jwtHelper.createJwt(userDetails);
        return token;
    }
}
