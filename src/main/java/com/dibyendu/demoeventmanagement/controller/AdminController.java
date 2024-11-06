package com.dibyendu.demoeventmanagement.controller;

import com.dibyendu.demoeventmanagement.models.*;
import com.dibyendu.demoeventmanagement.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UsersService usersService;
    @Autowired
    public AdminController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Operation(summary = "Add Fest", security = @SecurityRequirement(name = "basicAuth"))
    @PostMapping("/add-fest")
    public ResponseEntity<Response<String>> addFest(@RequestBody FestDto festDto){
        Boolean isSucceed = usersService.addFest(festDto);
        if (isSucceed) {
            return ResponseEntity.ok(new Response<>(HttpStatus.CREATED.value(), "Fest created"));
        }
        else {
            return ResponseEntity.ok(new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "Error creating fest"));
        }
    }
    @Operation(summary = "Add event to Fest", security = @SecurityRequirement(name = "basicAuth"))
    @PostMapping("/add-event")
    public ResponseEntity<Response<String>> addEvent(@RequestBody EventDto eventDto){
        Boolean isSucceed = usersService.addEventToFest(eventDto);
        if (isSucceed) {
            return ResponseEntity.ok(new Response<>(HttpStatus.CREATED.value(), "Event added"));
        }
        else {
            return ResponseEntity.ok(new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "Error adding event"));
        }
    }


        @PostMapping("/add-participants")
    public ResponseEntity<Response<String>> addParticipants(@RequestBody AddParticipantsDto addParticipantsDto){
            Boolean isSucceed = usersService.addParticipants(addParticipantsDto);
            if (isSucceed) {
                return ResponseEntity.ok(new Response<>(HttpStatus.CREATED.value(), "Participants added"));
            }
            else {
                return ResponseEntity.ok(new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "Error adding participants."));
            }
    }
    @Operation(summary = "Add volunteer", security = @SecurityRequirement(name = "basicAuth"))
    @PostMapping("/add-volunteer")
    public ResponseEntity<Response<String>> addVolunteer(@RequestBody SignUpDto signUpDto){
        Boolean isSucceed = usersService.signUpUser(signUpDto,false);
        if (isSucceed) {
            return ResponseEntity.ok(new Response<>(HttpStatus.CREATED.value(), "Volunteer added"));
        }
        else {
            return ResponseEntity.ok(new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "Volunteer creation failed"));
        }
    }
    @Operation(summary = "Assign volunteer to an event", security = @SecurityRequirement(name = "basicAuth"))
    @PostMapping("/assign-volunteer")
    public ResponseEntity<Response<String>> assignVolunteer(@RequestBody AssignVolunteer assignVolunteer){
        Boolean isSucceed = usersService.assignVolunteer(assignVolunteer);
        if (isSucceed) {
            return ResponseEntity.ok(new Response<>(HttpStatus.CREATED.value(), "Volunteer is assigned to the event."));
        }
        else {
            return ResponseEntity.ok(new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "error assigning volunteer."));
        }
    }

    @Operation(summary = "Fetch created fest list",security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping("/fetch-fests")
    public ResponseEntity<Response<List<Map<String, Instant>>>> fetchFests(){
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), usersService.fetchFests()));
    }
}
