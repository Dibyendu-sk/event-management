package com.dibyendu.demoeventmanagement.models.documents;

import com.dibyendu.demoeventmanagement.models.ParticipatedEventDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
public class Participants extends BaseDocument{
    @Id
    private String id;
    private String name;
    private String mobile;
    private String mail;
    private String institute;
    private String state;
    private String country;
    private String festId;
    private List<ParticipatedEventDto> participatedEvents;
}
