package com.baeldung.lsd.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@WebMvcTest(CampaignTaskController.class)
class CampaignTaskControllerLiveTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        Task task = new Task("Task name", "Task Description", LocalDate.now(), null);
        List<Task> tasks = Arrays.asList(task);
        Page<Task> tasksPage = new PageImpl<Task>(tasks);

        when(taskRepository.findByCampaignId(Mockito.anyLong(), Mockito.any())).thenReturn(tasksPage);

    }
    @Test
    void givenCampaignTasks_whenGetCampaignTasks_thenSuccess() throws Exception {

        mockMvc.perform(get("/campaigns/2/tasks").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalElements", is(1)))
            .andExpect(jsonPath("$.totalPages", is(1)))
            .andExpect(jsonPath("$.size", is(1)))
            .andExpect(jsonPath("$.numberOfElements", is(1)))
            .andExpect(jsonPath("$.content[0].name", is("Task name")));

    }
    @Test
    void givenPaginationInfo_whenGetCampaignTasks_thenSuccess() throws Exception {

        mockMvc.perform(get("/campaigns/1/tasks").param("page", "5")
            .param("size", "10")
            .param("sort", "name,desc"))
            .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(taskRepository).findByCampaignId(Mockito.anyLong(), pageableCaptor.capture());

        PageRequest pageable = (PageRequest) pageableCaptor.getValue();

        assertThat(pageable.isPaged()).isTrue();
        assertThat(pageable.getPageNumber()).isEqualTo(4);
        assertThat(pageable.getPageSize()).isEqualTo(10);
        assertThat(pageable.getSort()
            .isSorted()).isTrue();
        assertThat(pageable.getSort()
            .getOrderFor("name")).isEqualTo(new Sort.Order(Direction.DESC, "name"));

    }
}
