package com.baeldung.lsd.persistence.repository;

import java.util.List;

import com.baeldung.lsd.persistence.model.Task;

public interface CustomTaskRepository {
    List<Task> search(String searchParam);

    List<Task> findAll();
}
