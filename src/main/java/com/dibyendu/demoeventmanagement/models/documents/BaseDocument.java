package com.dibyendu.demoeventmanagement.models.documents;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

public class BaseDocument {
    @CreatedBy
    private String addedBy;

    @CreatedDate
    private Instant addedDate;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private Instant updatedDate;
}
