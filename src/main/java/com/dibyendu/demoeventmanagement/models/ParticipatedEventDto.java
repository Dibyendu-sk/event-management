package com.dibyendu.demoeventmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipatedEventDto {
    private String eventId;
    private EntryDetails entryDetails;
}
