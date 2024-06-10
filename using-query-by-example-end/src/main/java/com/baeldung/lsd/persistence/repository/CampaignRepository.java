package com.baeldung.lsd.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.lsd.persistence.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Iterable<Campaign> findByNameContaining(String name);
}
