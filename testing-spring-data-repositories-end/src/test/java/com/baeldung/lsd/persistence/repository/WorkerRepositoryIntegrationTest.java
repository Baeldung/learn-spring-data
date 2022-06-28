package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.baeldung.lsd.persistence.model.Worker;

@DataJpaTest
class WorkerRepositoryIntegrationTest {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void givenNewWorker_whenSaved_thenSuccess() {
        Worker newWorker = new Worker("johnTest1@test.com", "John", "Doe");
        assertThat(workerRepository.save(newWorker)).isEqualTo(entityManager.find(Worker.class, newWorker.getId()));
    }

    @Test
    void givenWorkerCreated_whenFindById_thenSuccess() {
        Worker newWorker = new Worker("johnTest2@test.com", "John", "Doe");
        workerRepository.save(newWorker);

        Optional<Worker> retrievedWorker = workerRepository.findById(newWorker.getId());
        assertThat(retrievedWorker.get()).isEqualTo(entityManager.find(Worker.class, newWorker.getId()));
    }
}