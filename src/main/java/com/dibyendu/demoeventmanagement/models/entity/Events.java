package com.dibyendu.demoeventmanagement.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Events extends BaseEntity{
    @Id
    private String id;
    @Column(name = "event_name")
    private String eventName;
    private String description;
//    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
//    @JoinColumn(name = "fest_id",referencedColumnName = "id")
//    private Fest fest;
}
