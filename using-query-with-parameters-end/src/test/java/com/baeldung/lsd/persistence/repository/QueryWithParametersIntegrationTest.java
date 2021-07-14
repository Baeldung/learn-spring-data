package com.baeldung.lsd.persistence.repository;

import com.baeldung.lsd.persistence.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QueryWithParametersIntegrationTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void whenFindByCodeIn_thenReturnExpectedResult() {

        Project newProject1 = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        Project newProject2 = new Project("PTEST-2", "Test Project 2", "Description for project PTEST-2");
        entityManager.persist(newProject1);
        entityManager.persist(newProject2);

        List<Project> result = projectRepository.findWithCodeIn(Set.of("PTEST-1", "PTEST-2"));
        assertThat(result).contains(newProject1, newProject2);
    }

    @Test
    void whenFindByNameAndDescriptionPositionalBind_thenReturnExpectedResult() {
        Project newProject1 = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        Project newProject2 = new Project("PTEST-2", "Test Project 2", "Description for project PTEST-2");
        entityManager.persist(newProject1);
        entityManager.persist(newProject2);

        List<Project> result = projectRepository.findWithNameAndDescriptionPositionalBind("Test Project 1", "Description for project PTEST-1");
        assertThat(result).containsOnly(newProject1);
    }

    @Test
    void whenFindByNameAndDescriptionNamedBind_thenReturnExpectedResult() {
        Project newProject1 = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        Project newProject2 = new Project("PTEST-2", "Test Project 2", "Description for project PTEST-2");
        entityManager.persist(newProject1);
        entityManager.persist(newProject2);

        List<Project> result = projectRepository.findWithNameAndDescriptionNamedBind("Description for project PTEST-1", "Test Project 1");
        assertThat(result).containsOnly(newProject1);
    }

    @Test
    void whenFindByDescriptionIsLike_thenReturnExpectedResult() {
        Project newProject1 = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        Project newProject2 = new Project("PTEST-2", "Test Project 2", "Description for project PTEST-2");
        entityManager.persist(newProject1);
        entityManager.persist(newProject2);

        List<Project> result = projectRepository.findWithDescriptionIsLike("for");
        assertThat(result).containsOnly(newProject1, newProject2);
    }

    @Test
    void whenFindByDescriptionIsShorterThan_thenReturnExpectedResult() {
        Project newProject1 = new Project("PTEST-1", "Test Project 1", "12345678");
        Project newProject2 = new Project("PTEST-2", "Test Project 2", "12345");
        Project newProject3 = new Project("PTEST-3", "Test Project 3", "12345");
        entityManager.persist(newProject1);
        entityManager.persist(newProject2);
        entityManager.persist(newProject3);

        List<Project> result = projectRepository.findWithDescriptionIsShorterThan(6);
        assertThat(result).containsOnly(newProject2, newProject3);

    }

    @Test
    void whenFindByDescriptionWthPrefixAndSuffix_thenReturnExpectedResult() {
        Project newProject1 = new Project("PTEST-1", "Test Project 1", "PRE Description for project PTEST-1 SUFFIX");
        Project newProject2 = new Project("PTEST-2", "Test Project 2", "PRE Description for project PTEST-2 BAR");
        entityManager.persist(newProject1);
        entityManager.persist(newProject2);

        List<Project> result = projectRepository.findWithDescriptionWithPrefixAndSuffix("PRE", "SUFFIX");
        assertThat(result).containsOnly(newProject1);
    }

    @Test
    void whenFindByDescriptionWthPrefixAndSuffixWithInjection_thenReturnExpectedResult() {
        Project newProject1 = new Project("PTEST-1", "Test Project 1", "PRE Description for project PTEST-1 SUFFIX");
        Project newProject2 = new Project("PTEST-2", "Test Project 2", "PRE Description for project PTEST-2 BAR");
        entityManager.persist(newProject1);
        entityManager.persist(newProject2);

        List<Project> result = projectRepository.findWithDescriptionWithPrefix("%");
        assertThat(result).hasSize(5);
    }
}
