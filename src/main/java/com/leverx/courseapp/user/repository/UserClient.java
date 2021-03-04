package com.leverx.courseapp.user.repository;

import com.leverx.courseapp.user.dto.UserDtoParam;
import com.okta.sdk.resource.user.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

public interface UserClient {

    User addUser(UserDtoParam userDto);

    void deleteUser(String email);
}
