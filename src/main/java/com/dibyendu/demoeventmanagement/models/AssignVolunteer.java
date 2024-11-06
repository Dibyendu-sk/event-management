package com.dibyendu.demoeventmanagement.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssignVolunteer {
    private String festId;
    private List<String> eventIds;
    private String volMail;
}
