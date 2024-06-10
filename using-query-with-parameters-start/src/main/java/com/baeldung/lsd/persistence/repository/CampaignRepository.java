package com.baeldung.lsd.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Campaign;

import java.util.List;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
    Iterable<Campaign> findByNameContaining(String name);

    @Query("select c from Campaign c where c.name='Campaign 3' and c.description='About Campaign 3'")
    List<Campaign> findWithNameAndDescription();
}
