package com.dibyendu.demoeventmanagement.models.entity;

import com.dibyendu.demoeventmanagement.models.EventsEnums;
import com.dibyendu.demoeventmanagement.models.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(name = "role")
    private String role= Roles.ADMIN.name();
    @Column(name = "associated_event")
    private String associatedEvent= EventsEnums.ALL.name();
}
