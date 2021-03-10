package com.leverx.courseapp.user.repository;

import com.leverx.courseapp.user.dto.UserDtoParam;


public interface UserClient {

    void addUser(UserDtoParam userDto);

    void deleteUser(String email);
}
