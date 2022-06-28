package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.Worker;

@SpringBootTest
@Transactional
class WorkerRepositoryIntegrationTest {

    @Autowired
    WorkerRepository workerRepository;

    @Test
    void givenNewWorker_whenSaved_thenSuccess() {
        Worker newWorker = new Worker("johndoe@test.com", "John", "Doe");
        assertThat(workerRepository.save(newWorker)).isNotNull();
    }

    @Test
    void givenWorkerCreated_whenFindById_thenSuccess() {
        Worker newWorker = new Worker("johndoe@test.com", "John", "Doe");
        assertThat(workerRepository.save(newWorker)).isNotNull();

        Optional<Worker> retrievedWorker = workerRepository.findById(newWorker.getId());
        assertThat(retrievedWorker.get()).isEqualTo(newWorker);
    }
}