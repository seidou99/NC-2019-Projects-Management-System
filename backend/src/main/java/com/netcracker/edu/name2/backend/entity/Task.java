package com.netcracker.edu.name2.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(targetEntity = Project.class)
    private Project project;

    @Column(length = 4, nullable = false)
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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
    private Date updated;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date estimation;

    @ManyToOne(targetEntity = User.class)
    private User assignee;

    @NotNull
    @ManyToOne(targetEntity = User.class)
//    @JoinColumn(name = "reporter_id", referencedColumnName = "id", table = "user")
    private User reporter;

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", project=" + project +
                ", code=" + code +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", created=" + created +
                ", dueDate=" + dueDate +
                ", updated=" + updated +
                ", estimation=" + estimation +
                ", assignee=" + assignee +
                ", reporter=" + reporter +
                '}';
    }
}
