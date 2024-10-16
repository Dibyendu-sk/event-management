package com.dibyendu.demoeventmanagement.controller;

import com.dibyendu.demoeventmanagement.models.Response;
import com.dibyendu.demoeventmanagement.models.SignUpDto;
import com.dibyendu.demoeventmanagement.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exposed")
public class ExposedController {
    private final UsersService usersService;

    @Autowired
    public ExposedController(UsersService usersService) {
        this.usersService = usersService;
    }
    @Operation(summary="create admin")
    @PostMapping("/create-admin")
    public ResponseEntity<Response<String>> signUpAsAdmin(@RequestBody SignUpDto signUpDto) {
        Boolean isSucceed = usersService.signUpUser(signUpDto,true);
        if (isSucceed) {
            return ResponseEntity.ok(new Response<>(HttpStatus.CREATED.value(), "Admin created successfully"));
        }
        else {
            return ResponseEntity.ok(new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "Admin creation failed"));
        }
    }
}
