package com.dibyendu.demoeventmanagement.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDto {
    private String eventName;
    private String description;
    private String shortForm;
    private String festId;
}
