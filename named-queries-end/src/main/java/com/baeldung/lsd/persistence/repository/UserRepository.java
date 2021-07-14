package com.baeldung.lsd.persistence.repository;

import com.baeldung.lsd.persistence.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
