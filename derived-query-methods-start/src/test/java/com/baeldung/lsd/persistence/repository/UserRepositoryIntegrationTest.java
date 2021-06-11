package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.lsd.DerivedQueryMethodsApp;
import com.baeldung.lsd.persistence.model.User;

@ExtendWith(SpringExtension.class)
// avoid running the DerivedQueryMethodsApp#run method
@ContextConfiguration(classes = { DerivedQueryMethodsApp.class }, initializers = ConfigDataApplicationContextInitializer.class)
@Transactional
class UserRepositoryIntegrationTest {

    @Autowired
    IUserRepository userRepository;

    @Test
    void givenNewUser_whenSaved_thenSuccess() {
        User newUser = new User("johnTest1@test.com", "John", "Doe");
        assertThat(userRepository.save(newUser)).isNotNull();
    }

    @Test
    void givenUserCreated_whenFindById_thenSuccess() {
        User newUser = new User("johnTest2@test.com", "John", "Doe");
        assertThat(userRepository.save(newUser)).isNotNull();

        Optional<User> retrievedUser = userRepository.findById(newUser.getId());
        assertThat(retrievedUser.get()).isEqualTo(newUser);
    }
}