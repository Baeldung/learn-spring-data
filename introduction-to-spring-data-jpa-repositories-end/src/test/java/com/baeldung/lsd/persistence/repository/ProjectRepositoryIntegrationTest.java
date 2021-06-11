package com.baeldung.lsd.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.lsd.persistence.model.Project;

@SpringBootTest
class ProjectRepositoryIntegrationTest {

    @Autowired
    IProjectRepository projectRepository;

    @Test
    void givenLoadedProjects_whenFindByID_thenSuccess() {
        Optional<Project> savedProject = projectRepository.findById(1L);
        assertNotNull(savedProject.get());
    }
}