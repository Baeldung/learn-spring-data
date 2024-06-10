package com.baeldung.lsd.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.Campaign;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
    
    @Transactional(readOnly = true)
    Iterable<Campaign> findByNameContaining(String name);
}
