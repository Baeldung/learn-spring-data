package com.baeldung.lsd.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOError;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;
import com.baeldung.lsd.persistence.repository.ProjectRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
class ProjectServiceUnitTest {
    
    @InjectMocks
    private ProjectService projectService;
    
    @Mock
    private TaskRepository taskRepository;
    
    @Mock
    private ProjectRepository projectRepository;
    
    private Project project;
    
    private Task task;
    
    @BeforeEach
    void setMockOutput() {
        project = new Project("Mock Code", "Mock Name", "Mock Description");
        project.setId(1L);
        
        task = new Task("Mock Task", "Mock Description", null, project, TaskStatus.IN_PROGRESS);
        task.setId(1L);
        task.setProject(project);
        project.setTasks(Set.<Task>of(task));
        
    }
    
    @Test
    void givenOverDueTasks_whenEndProject_thenException() {
        
        Throwable throwable = catchThrowable(() -> {
            projectService.endProject(project);
        });
        
        assertThat(throwable).isInstanceOf(IOError.class);
        assertThat(task.getProject()).isNotNull().isNotEqualTo(project);
        assertThat(task.getProject().getName()).contains(" - Cont");
        assertThat(task.getProject().getCode()).contains("-CONT");
    }

}
