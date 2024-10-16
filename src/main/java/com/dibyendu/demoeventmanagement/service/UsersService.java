package com.dibyendu.demoeventmanagement.service;

import com.dibyendu.demoeventmanagement.models.SignUpDto;

public interface UsersService {
    Boolean signUpUser(SignUpDto signUpDto,boolean isAdmin);
}
