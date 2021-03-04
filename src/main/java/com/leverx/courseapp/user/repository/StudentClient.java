package com.leverx.courseapp.user.repository;

import com.leverx.courseapp.user.dto.StudentDtoParam;
import com.leverx.courseapp.user.dto.UserDtoParam;
import com.leverx.courseapp.user.exception.NoSuchStudentException;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudentClient implements UserClient {

    @Value("${okta.group.users.id}")
    private String groupId;

    @NonNull
    private final Client client;

    @Override
    public User addUser(UserDtoParam userDtoParam) {
        var user = UserBuilder.instance()
                .addGroup(groupId)
                .setEmail(userDtoParam.getEmail())
                .setFirstName(userDtoParam.getFirstName())
                .setLastName(userDtoParam.getLastName())
                .setPassword(userDtoParam.getPassword())
                .buildAndCreate(client);
        return user;
    }

    @Override
    public void deleteUser(String email) {
        var user =
                client.listUsers().stream()
                        .filter(searchedUser -> searchedUser.getProfile().getEmail().equals(email))
                        .findFirst()
                        .orElseThrow(NoSuchStudentException::new);
        user.deactivate();
        user.delete();
    }
}
