package com.dibyendu.demoeventmanagement.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventAddDto {
    private String eventName;
    private String description;
    private String shortForm;
    private String festId;
}
