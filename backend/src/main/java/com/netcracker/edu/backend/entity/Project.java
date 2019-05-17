package com.netcracker.edu.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 5)
    @Column(length = 5, nullable = false, unique = true)
    private String code;

    @Size(min = 20, max = 200)
    @Column(length = 200, nullable = false)
    private String summary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
