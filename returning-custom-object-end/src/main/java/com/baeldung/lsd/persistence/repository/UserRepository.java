package com.baeldung.lsd.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.User;
import com.baeldung.lsd.persistence.projection.UserOpen;

public interface UserRepository extends CrudRepository<User, Long> {

    List<UserOpen> findByFirstName(String firstName);

}
