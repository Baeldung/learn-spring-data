package com.baeldung.lsd.persistence.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;

public class CustomTaskRepositoryImpl implements CustomTaskRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Task> search(String searchParam) {
        String searchQueryParam = "%" + String.join("%", searchParam.split(" ")) + "%";
        TypedQuery<Task> query = entityManager.createQuery("select t from Task t where description like ?1", Task.class);
        query.setParameter(1, searchQueryParam);
        return query.getResultList();
    }

    @Override
    public List<Task> findAll() {
        TypedQuery<Task> query = entityManager.createQuery("select t from Task t where status != ?1", Task.class);
        query.setParameter(1, TaskStatus.DONE);
        return query.getResultList();
    }
}
