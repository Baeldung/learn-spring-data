package com.baeldung.lsd.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.baeldung.lsd.persistence.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Iterable<Project> findByNameContaining(String name);

    @Query("select p from Project p where p.name='Project 3' and p.description='About Project 3'")
    List<Project> findWithNameAndDescription();

    @Query("select p from Project p where p.name=?1 and p.description=?2")
    List<Project> findWithNameAndDescriptionPositionalBind(String name, String description);

    @Query("select p from Project p where p.name=:name and p.description=:description")
    List<Project> findWithNameAndDescriptionNamedBind(@Param("description") String description, @Param("name") String name);

    @Query("select p from Project p where p.code in ?1")
    List<Project> findWithCodeIn(Collection<String> codes);

    @Query("select p from Project p where p.description like %:keyword%")
    List<Project> findWithDescriptionIsLike(@Param("keyword") String keyword);

    @Query("select p from Project p where p.description like CONCAT(:prefix, '%', :suffix)")
    List<Project> findWithDescriptionWithPrefixAndSuffix(@Param("prefix") String prefix, @Param("suffix") String suffix);

    @Query("select p from Project p where p.description like :prefix%")
    List<Project> findWithDescriptionWithPrefix(@Param("prefix") String prefix);

    @Query(value = "select * from Project p where LENGTH(p.description) < :length", nativeQuery = true)
    List<Project> findWithDescriptionIsShorterThan(@Param("length") int len);

}
