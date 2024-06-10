package com.baeldung.lsd.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.baeldung.lsd.persistence.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    Page<Task> findByCampaignId(long id, Pageable pageable);
}
