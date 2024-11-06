package com.dibyendu.demoeventmanagement.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Fest extends BaseEntity {
    @Id
    private String id;
    @Column(name = "fest_name")
    private String festName;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "fest_id")
    private Set<Events> events;
}
