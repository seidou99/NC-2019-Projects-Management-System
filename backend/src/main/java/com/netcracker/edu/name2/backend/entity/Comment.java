package com.netcracker.edu.name2.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(targetEntity = User.class)
    private User author;

    @Column(nullable = false)
    private String text;

    @ManyToOne(targetEntity = Task.class, fetch = FetchType.LAZY)
    private Task task;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
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
                Objects.equals(task, comment.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, text, task);
    }


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", author=" + author +
                ", text='" + text + '\'' +
                ", task=" + task +
                '}';
    }
}
