package com.netcracker.edu.name2.backend.entity;

import javax.persistence.*;

@Entity
public class TaskPriority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String name;

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

    @Override
    public String toString() {
        return "TaskPriority{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
