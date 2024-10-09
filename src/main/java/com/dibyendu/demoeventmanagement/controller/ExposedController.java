package com.dibyendu.demoeventmanagement.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exposed")
public class ExposedController {
    @PostMapping("/create-admin")
}
