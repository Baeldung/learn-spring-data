package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.User;

@SpringBootTest
@Transactional
class UserRepositoryIntegrationTest {

    @Autowired
    IUserRepository userRepository;

    @Test
    void givenNewUser_whenSaved_thenSuccess() {
        User newUser = new User("johndoe@test.com", "John", "Doe");
        assertThat(userRepository.save(newUser)).isNotNull();
    }

    @Test
    void givenUserCreated_whenFindById_thenSuccess() {
        User newUser = new User("johndoe@test.com", "John", "Doe");
        assertThat(userRepository.save(newUser)).isNotNull();

        Optional<User> retrievedUser = userRepository.findById(newUser.getId());
        assertThat(retrievedUser.get()).isEqualTo(newUser);
    }
}