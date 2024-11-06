package com.dibyendu.demoeventmanagement.models;

import com.dibyendu.demoeventmanagement.models.documents.Participants;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddParticipantsDto {
    private String festId;
    private List<Participants> participants;
}
