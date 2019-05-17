package com.netcracker.edu.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String name;

    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String surname;

    @NotNull
    @ManyToOne(targetEntity = UserRole.class)
    private UserRole role;

    @NotNull
    @OneToOne(targetEntity = UserAuthData.class)
    private UserAuthData authData;

    public void setId(Long id) {
        this.id = id;
    }

    public UserAuthData getAuthData() {
        return authData;
    }

    public void setAuthData(UserAuthData authData) {
        this.authData = authData;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fapi='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                ", authData=" + authData +
                '}';
    }
}
