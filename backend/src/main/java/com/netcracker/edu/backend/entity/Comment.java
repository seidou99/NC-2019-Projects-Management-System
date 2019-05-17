package com.netcracker.edu.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Author is required")
    @OneToOne(targetEntity = User.class)
    private User author;

    @Size(min = 3, max = 400, message = "Comment size must be between 3 and 400 symbols")
    @NotBlank(message = "Comment text is required")
    @Column(length = 400)
    private String text;

    @NotNull(message = "Task is required")
    @ManyToOne(targetEntity = Task.class, fetch = FetchType.LAZY)
    private Task task;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonIgnore
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(author, comment.author) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(task, comment.task) &&
                Objects.equals(date, comment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, text, task, date);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", author=" + author +
                ", text='" + text + '\'' +
                ", task=" + task +
                ", date=" + date +
                '}';
    }
}
