package com.dibyendu.demoeventmanagement.models.entity;

import com.dibyendu.demoeventmanagement.models.EventsEnums;
import com.dibyendu.demoeventmanagement.models.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class UserInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(name = "role")
    private String role;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},fetch = FetchType.EAGER)
    @JoinTable(
            name = "volunteer_event",  // Join table name
            joinColumns = @JoinColumn(name = "volunteer_id"),  // Foreign key to Volunteer
            inverseJoinColumns = @JoinColumn(name = "event_id")  // Foreign key to Event
    )
    private Set<Events> associatedEvents;
//    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @JoinColumn(name = "fest_id",referencedColumnName = "id")
//    private Fest festId;
}
