package com.baeldung.lsd.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
