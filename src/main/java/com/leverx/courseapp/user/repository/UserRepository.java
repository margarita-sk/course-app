package com.leverx.courseapp.user.repository;

import com.leverx.courseapp.user.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

  User findByName(String name);
}
