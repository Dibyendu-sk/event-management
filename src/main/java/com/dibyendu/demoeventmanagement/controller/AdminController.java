package com.dibyendu.demoeventmanagement.controller;

import com.dibyendu.demoeventmanagement.models.Response;
import com.dibyendu.demoeventmanagement.models.SignUpDto;
import com.dibyendu.demoeventmanagement.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UsersService usersService;
    @Autowired
    public AdminController(UsersService usersService) {
        this.usersService = usersService;
    }

    //    @GetMapping("/add-participants")
//    public ResponseEntity<String> addParticipants()
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
}
