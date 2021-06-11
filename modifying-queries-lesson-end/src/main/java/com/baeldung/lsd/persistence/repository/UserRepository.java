package com.baeldung.lsd.persistence.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
    @Modifying
    @Query(value = "alter table User add column active int(1) not null default 1", nativeQuery = true)
    void addActiveColumn();

}
