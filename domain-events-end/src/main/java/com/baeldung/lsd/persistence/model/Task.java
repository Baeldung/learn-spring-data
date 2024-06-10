package com.baeldung.lsd.persistence.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;
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

    @NaturalId
    @Column(unique = true, nullable = false, updatable = false)
    private String uuid = UUID.randomUUID()
        .toString();

    private String name;

    private String description;

    private LocalDate dueDate;

    private TaskStatus status;

    @ManyToOne(optional = false)
    private Campaign campaign;

    @ManyToOne
    private Worker assignee;

    public Task() {
    }

    public Task(String name, String description, LocalDate dueDate, Campaign campaign, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.campaign = campaign;
    }

    public Task(String name, String description, LocalDate dueDate, Campaign campaign) {
        this(name, description, dueDate, campaign, TaskStatus.TO_DO);
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

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
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
        return "Task [id=" + id + ", name=" + name + ", description=" + description + ", dueDate=" + dueDate + ", status=" + status + ", campaign=" + campaign
                + ", assignee=" + assignee + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Task other)) return false;

        return Objects.equals(getUuid(), other.getUuid());
    }

}
