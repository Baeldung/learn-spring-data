package com.baeldung.lsd.persistence.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import com.baeldung.lsd.domain.task.model.TaskUpdated;

@Entity
public class Task {
    
    private static final Logger LOG = LoggerFactory.getLogger(Task.class);
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String uuid = UUID.randomUUID()
        .toString();

    private String name;

    private String description;

    private LocalDate dueDate;

    private TaskStatus status;

    @ManyToOne(optional = false)
    private Project project;

    @ManyToOne
    private Worker assignee;

    public Task() {
    }

    public Task(String name, String description, LocalDate dueDate, Project project, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.project = project;
    }

    public Task(String name, String description, LocalDate dueDate, Project project) {
        this(name, description, dueDate, project, TaskStatus.TO_DO);
    }
    
    // Retrieve Domain Event(s) here
    @DomainEvents
    public List<Object> domainEvents(){
        return List.of(new TaskUpdated(this));
    }

    // Callback after Publishing Domain Events
    @AfterDomainEventPublication
    void callback() {
        LOG.info("Task Domain Events Published!");
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Worker getAssignee() {
        return assignee;
    }

    public void setAssignee(Worker assignee) {
        this.assignee = assignee;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", name=" + name + ", description=" + description + ", dueDate=" + dueDate + ", status=" + status + ", project=" + project + ", assignee=" + assignee + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        if (uuid == null) {
            if (other.uuid != null)
                return false;
        } else if (!uuid.equals(other.uuid))
            return false;
        return true;
    }

}
