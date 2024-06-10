package com.baeldung.lsd.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Campaign;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
    Iterable<Campaign> findByName(String name);

    Iterable<Campaign> findByNameIsNot(String name);

    Iterable<Campaign> findByNameStartingWith(String name);

    Iterable<Campaign> findByNameEndingWith(String name);

    Iterable<Campaign> findByNameContaining(String name);

    Iterable<Campaign> findByNameLike(String likePattern);

    Iterable<Campaign> findDistinctByTasksNameContaining(String taskName);
}
