package com.leverx.courseapp.security.util;

import com.leverx.courseapp.security.exception.WrongPasswordException;
import com.leverx.courseapp.user.repository.UserRepository;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        var user =
                Optional.ofNullable(userRepository.findByName(name))
                        .orElseThrow(
                                () -> {
                                    throw new UsernameNotFoundException(name + " not found");
                                });
        var userDetails = new CustomUserDetails(user);
        return userDetails;
    }

    public UserDetails findByNameAndPassword(String name, String password) throws WrongPasswordException {
        var user = loadUserByUsername(name);
        System.out.println(user.getPassword());
        System.out.println(password);
        if(bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new WrongPasswordException();
        }
    }


}
