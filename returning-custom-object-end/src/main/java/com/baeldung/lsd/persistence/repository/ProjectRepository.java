package com.baeldung.lsd.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.projection.ProjectClosed;
import com.baeldung.lsd.persistence.projection.ProjectNative;
import com.baeldung.lsd.persistence.projection.ProjectClass;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Iterable<Project> findByNameContaining(String name);

    List<ProjectClosed> findClosedByNameContaining(String name);

    List<ProjectClass> findClassByNameContaining(String name);

    @Query(nativeQuery = true, value = "SELECT p.id, p.name, count(t.id) AS taskCount" // @formatter:off
        + " FROM project p"
        + " LEFT JOIN task t ON p.id=t.project_id "
        + " GROUP BY p.id") // @formatter:on
    List<ProjectNative> getProjectStatistics();

}
