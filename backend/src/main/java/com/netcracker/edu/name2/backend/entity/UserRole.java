package com.netcracker.edu.name2.backend.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @OneToMany(targetEntity = User.class, mappedBy = "id", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();
}
