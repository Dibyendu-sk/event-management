package com.dibyendu.demoeventmanagement.models;

import lombok.Data;

@Data
public class ParticipantVerifyReq {
    private String festId;
    private String name;
    private String reg;
    private String eventId;
}
