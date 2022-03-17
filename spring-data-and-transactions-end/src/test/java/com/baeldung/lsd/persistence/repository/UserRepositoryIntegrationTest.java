package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.User;

@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
class UserRepositoryIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void givenNewUser_whenSaved_thenSuccess() {
        User newUser = new User("johnTest1@test.com", "John", "Doe");
        assertThat(userRepository.save(newUser)).isEqualTo(entityManager.find(User.class, newUser.getId()));
    }

    @Test
    void givenUserCreated_whenFindById_thenSuccess() {
        User newUser = new User("johnTest2@test.com", "John", "Doe");
        entityManager.persist(newUser);

        Optional<User> retrievedUser = userRepository.findById(newUser.getId());
        assertThat(retrievedUser.get()).isEqualTo(entityManager.find(User.class, newUser.getId()));
    }
}