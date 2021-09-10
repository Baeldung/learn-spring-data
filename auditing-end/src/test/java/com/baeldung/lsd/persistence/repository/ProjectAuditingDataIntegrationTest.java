package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.lsd.persistence.model.AuditingData;
import com.baeldung.lsd.persistence.model.Project;

@SpringBootTest
class ProjectAuditingDataIntegrationTest {

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void givenProjectCreated_whenSave_thenAuditFieldsAutoSet() {
        Project newProject = new Project("PTEST-AUD1", "Test Project - Auditing Data 1", "Description for project PTEST-AUD1");

        AuditingData auditingData = projectRepository.save(newProject)
            .getAuditingData();

        assertThat(auditingData).isNotNull();
        assertThat(auditingData.getCreatedDate()).isNotNull();
        assertThat(auditingData.getLastModifiedDate()).isNotNull();
        assertThat(auditingData.getCreatedBy()).isNotNull();
        assertThat(auditingData.getLastModifiedBy()).isNotNull();
    }

    @Test
    void givenProjectCreated_whenUpdate_thenLastModifiedDateUpdated() {
        Project newProject = new Project("PTEST-AUD2", "Test Project  - Auditing Data 2", "Description for project PTEST-AUD2");
        newProject = projectRepository.save(newProject);

        newProject.setName("Test Project Aud 2 updated");
        AuditingData auditingData = projectRepository.save(newProject)
            .getAuditingData();

        assertThat(auditingData.getLastModifiedDate()).isAfter(auditingData.getCreatedDate());
    }
}