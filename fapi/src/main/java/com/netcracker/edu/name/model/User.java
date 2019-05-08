package com.netcracker.edu.name.model;

import java.util.Objects;

public class User {

    private Long id;
    private String name;
    private String surname;
    private UserRole role;
    private UserAuthData authData;

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

    public UserAuthData getAuthData() {
        return authData;
    }

    public void setAuthData(UserAuthData authData) {
        this.authData = authData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(role, user.role) &&
                Objects.equals(authData, user.authData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, role, authData);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                ", authData=" + authData +
                '}';
    }
}
