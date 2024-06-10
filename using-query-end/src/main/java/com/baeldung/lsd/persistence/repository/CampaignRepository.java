package com.baeldung.lsd.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Campaign;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
    Iterable<Campaign> findByNameContaining(String name);

    @Query(value = "select c from Campaign as c where c.name='Campaign 3' and c.description='About Campaign 3'")
    List<Campaign> findWithNameAndDescription();

    @Query(value = "select c.name from Campaign as c where c.code='C1'")
    Optional<String> findNameByCode();

    @Query(nativeQuery = true, value = "select * from campaign limit 1")
    Campaign findSingleCampaign();
}
