package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.lsd.DerivedQueryMethodsApp;
import com.baeldung.lsd.persistence.model.Worker;

@ExtendWith(SpringExtension.class)
// avoid running the DerivedQueryMethodsApp#run method
@ContextConfiguration(classes = { DerivedQueryMethodsApp.class }, initializers = ConfigDataApplicationContextInitializer.class)
@Transactional
class WorkerRepositoryIntegrationTest {

    @Autowired
    WorkerRepository workerRepository;

    @Test
    void givenNewWorker_whenSaved_thenSuccess() {
        Worker newWorker = new Worker("johnTest1@test.com", "John", "Doe");
        assertThat(workerRepository.save(newWorker)).isNotNull();
    }

    @Test
    void givenWorkerCreated_whenFindById_thenSuccess() {
        Worker newWorker = new Worker("johnTest2@test.com", "John", "Doe");
        assertThat(workerRepository.save(newWorker)).isNotNull();

        Optional<Worker> retrievedWorker = workerRepository.findById(newWorker.getId());
        assertThat(retrievedWorker.get()).isEqualTo(newWorker);
    }
}