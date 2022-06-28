package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.Worker;

@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
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
        entityManager.persist(newWorker);

        Optional<Worker> retrievedWorker = workerRepository.findById(newWorker.getId());
        assertThat(retrievedWorker.get()).isEqualTo(entityManager.find(Worker.class, newWorker.getId()));
    }
}