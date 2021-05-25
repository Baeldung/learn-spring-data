package com.baeldung.lsd.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.User;

public interface IUserRepository extends CrudRepository<User, Long> {

}
