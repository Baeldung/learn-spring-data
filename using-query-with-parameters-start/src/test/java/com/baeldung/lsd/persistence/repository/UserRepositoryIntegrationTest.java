package com.baeldung.lsd.persistence.repository;

import com.baeldung.lsd.persistence.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
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
        userRepository.save(newUser);

        Optional<User> retrievedUser = userRepository.findById(newUser.getId());
        assertThat(retrievedUser.get()).isEqualTo(entityManager.find(User.class, newUser.getId()));
    }
}