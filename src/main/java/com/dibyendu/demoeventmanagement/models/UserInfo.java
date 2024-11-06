package com.dibyendu.demoeventmanagement.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserInfo {
    private String email;
    private Set<String> assignedEvents;
}
