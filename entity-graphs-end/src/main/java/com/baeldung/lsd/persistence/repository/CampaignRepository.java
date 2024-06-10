package com.baeldung.lsd.persistence.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Campaign;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
    
    @EntityGraph(value = "campaign-with-tasks")
    Iterable<Campaign> findByNameContaining(String name);
}
