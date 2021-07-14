package com.baeldung.lsd.persistence.repository;

import com.baeldung.lsd.persistence.model.Project;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    Iterable<Project> findByNameContaining(String name);

    List<Project> findProjectsWithIdLessThan(@Param("id") Long id);

    List<Project> findProjectsWithDescriptionPrefix(@Param("prefix") String prefix);

    List<Project> findProjectsWithDescriptionShorterThan(@Param("length") int length);

    @Modifying(clearAutomatically = true)
    int updateProjectDescriptionById(@Param("id") Long id, @Param("newDescription") String newDescription);
}
