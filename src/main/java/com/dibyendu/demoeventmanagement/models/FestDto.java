package com.dibyendu.demoeventmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class FestDto {
    private String festId;
    private String festName;
    private Instant createdDate;
}
