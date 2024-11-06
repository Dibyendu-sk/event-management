package com.dibyendu.demoeventmanagement.controller;

import com.dibyendu.demoeventmanagement.models.ParticipantVerifyReq;
import com.dibyendu.demoeventmanagement.models.Response;
import com.dibyendu.demoeventmanagement.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/volunteer")
public class VolunteerController {
    private final UsersService usersService;

    public VolunteerController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Operation(summary = "Add Fest", security = @SecurityRequirement(name = "basicAuth"))
    @PostMapping("/verifyParticipant")
    public ResponseEntity<Response<String>> verifyParticipant(@RequestBody ParticipantVerifyReq participantVerifyReq){
        Boolean isSucceed = usersService.verifyParticipant(participantVerifyReq);
        if (isSucceed) {
            return ResponseEntity.ok(new Response<>(HttpStatus.CREATED.value(), "Participant verified"));
        }
        else {
            return ResponseEntity.ok(new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "Error verifying participant"));
        }
    }
}
