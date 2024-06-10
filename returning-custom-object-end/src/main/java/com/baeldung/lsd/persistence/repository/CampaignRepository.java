package com.baeldung.lsd.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.projection.CampaignClosed;
import com.baeldung.lsd.persistence.projection.CampaignNative;
import com.baeldung.lsd.persistence.projection.CampaignClass;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
    Iterable<Campaign> findByNameContaining(String name);

    List<CampaignClosed> findClosedByNameContaining(String name);

    List<CampaignClass> findClassByNameContaining(String name);

    @Query(nativeQuery = true, value = "SELECT c.id, c.name, count(t.id) AS taskCount" // @formatter:off
        + " FROM campaign c"
        + " LEFT JOIN task t ON c.id=t.campaign_id "
        + " GROUP BY c.id") // @formatter:on
    List<CampaignNative> getCampaignStatistics();

}
