package com.dibyendu.demoeventmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventDto {
    private String eventId;
    private String eventName;
    private String eventDesc;
}
