package com.baeldung.lsd.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.repository.ProjectRepository;

@WebMvcTest(ProjectController.class)
class ProjectControllerLiveTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectRepository projectRepository;

    @Test
    void givenProjects_whenAccessProjects_thenSuccess() throws Exception {
        Project project1 = new Project(UUID.randomUUID()
            .toString(), "Project One", "Projet One Description");
        Project project2 = new Project(UUID.randomUUID()
            .toString(), "Project Two", "Projet Two Description");
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        mockMvc.perform(get("/projects"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$.[0].name", is(project1.getName())))
            .andExpect(jsonPath("$.[1].name", is(project2.getName())));

    }

}
