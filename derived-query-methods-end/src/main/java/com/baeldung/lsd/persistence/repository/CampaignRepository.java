package com.baeldung.lsd.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Campaign;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {

    Optional<Campaign> findByCodeEquals(String code);

    int countByName(String name);
}
