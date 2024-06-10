package com.baeldung.lsd.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.baeldung.lsd.persistence.model.Campaign;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
    Iterable<Campaign> findByNameContaining(String name);

    @Query("select c from Campaign c where c.name='Campaign 3' and c.description='About Campaign 3'")
    List<Campaign> findWithNameAndDescription();

    @Query("select c from Campaign c where c.name=?1 and c.description=?2")
    List<Campaign> findWithNameAndDescriptionPositionalBind(String name, String description);

    @Query("select c from Campaign c where c.name=:name and c.description=:description")
    List<Campaign> findWithNameAndDescriptionNamedBind(@Param("description") String description, @Param("name") String name);

    @Query("select c from Campaign c where c.code in ?1")
    List<Campaign> findWithCodeIn(Collection<String> codes);

    @Query("select c from Campaign c where c.description like %:keyword%")
    List<Campaign> findWithDescriptionIsLike(@Param("keyword") String keyword);

    @Query("select c from Campaign c where c.description like CONCAT(:prefix, '%', :suffix)")
    List<Campaign> findWithDescriptionWithPrefixAndSuffix(@Param("prefix") String prefix, @Param("suffix") String suffix);

    @Query("select c from Campaign c where c.description like :prefix%")
    List<Campaign> findWithDescriptionWithPrefix(@Param("prefix") String prefix);

    @Query(value = "select * from Campaign c where LENGTH(c.description) < :length", nativeQuery = true)
    List<Campaign> findWithDescriptionIsShorterThan(@Param("length") int len);

}
