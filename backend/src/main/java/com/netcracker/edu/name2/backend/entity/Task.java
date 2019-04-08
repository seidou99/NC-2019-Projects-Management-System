package com.netcracker.edu.name2.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.netcracker.edu.name2.backend.entity.TaskPriority;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(targetEntity = Project.class)
    private Project project;

    @Column(nullable = false)
    private Long code;

    @Column(length = 200, nullable = false)
    private String description;

    @NotNull
    @ManyToOne(targetEntity = TaskPriority.class)
    private TaskPriority priority;

    @NotNull
    @ManyToOne(targetEntity = TaskStatus.class)
    private TaskStatus status;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @NotNull
    private int estimation;

    private int logWork;

    @ManyToOne(targetEntity = User.class)
    private User assignee;

    @NotNull
    @ManyToOne(targetEntity = User.class)
    private User reporter;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getEstimation() {
        return estimation;
    }

    public void setEstimation(int estimation) {
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

    public int getLogWork() {
        return logWork;
    }

    public void setLogWork(int logWork) {
        this.logWork = logWork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return code == task.code &&
                estimation == task.estimation &&
                logWork == task.logWork &&
                Objects.equals(id, task.id) &&
                Objects.equals(project, task.project) &&
                Objects.equals(description, task.description) &&
                Objects.equals(priority, task.priority) &&
                Objects.equals(status, task.status) &&
                Objects.equals(created, task.created) &&
                Objects.equals(dueDate, task.dueDate) &&
                Objects.equals(updated, task.updated) &&
                Objects.equals(assignee, task.assignee) &&
                Objects.equals(reporter, task.reporter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, project, code, description, priority, status, created, dueDate, updated, estimation, logWork, assignee, reporter);
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
                ", logWork=" + logWork +
                ", assignee=" + assignee +
                ", reporter=" + reporter +
                '}';
    }
}
