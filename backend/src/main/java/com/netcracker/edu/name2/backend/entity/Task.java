package com.netcracker.edu.name2.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Project.class)
    private Project project;

    @Column(length = 200, nullable = false)
    private String description;

    @Column(length = 20, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TaskPriority priority;

    @Column(length = 20, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TaksStatus status;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date estimation;

    @ManyToOne(targetEntity = User.class)
    private User assignee;

    public Long getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaksStatus getStatus() {
        return status;
    }

    public void setStatus(TaksStatus status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getEstimation() {
        return estimation;
    }

    public void setEstimation(Date estimation) {
        this.estimation = estimation;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
