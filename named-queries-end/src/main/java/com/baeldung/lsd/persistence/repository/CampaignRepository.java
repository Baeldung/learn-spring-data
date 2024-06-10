package com.baeldung.lsd.persistence.repository;

import com.baeldung.lsd.persistence.model.Campaign;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {

    Iterable<Campaign> findByNameContaining(String name);

    List<Campaign> findCampaignsWithIdLessThan(@Param("id") Long id);

    List<Campaign> findCampaignsWithDescriptionPrefix(@Param("prefix") String prefix);

    List<Campaign> findCampaignsWithDescriptionShorterThan(@Param("length") int length);

    @Modifying(clearAutomatically = true)
    int updateCampaignDescriptionById(@Param("id") Long id, @Param("newDescription") String newDescription);
}
