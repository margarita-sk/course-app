package com.leverx.courseapp.user.controller;

import com.leverx.courseapp.security.jwt.JwtTokenProvider;
import com.leverx.courseapp.security.util.CustomUserDetails;
import com.leverx.courseapp.security.util.UserDetailsServiceImpl;
import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.dto.UserDto;
import com.leverx.courseapp.user.model.Role;
import com.leverx.courseapp.user.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@AllArgsConstructor
public class AuthController {

    private final UserDetailsServiceImpl userService;
    private final JwtTokenProvider jwtProvider;


    @PostMapping("/auth")
    public String auth(@RequestBody UserDto userDto) {
        var user = userService.findByNameAndPassword(userDto.getUsername(), userDto.getPassword().toString());
        var token = jwtProvider.createToken(user.getUsername(), (Collection<GrantedAuthority>) user.getAuthorities());
        return token;
    }
}
