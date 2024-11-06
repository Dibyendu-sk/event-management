package com.dibyendu.demoeventmanagement.service;

import com.dibyendu.demoeventmanagement.models.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface UsersService {
    Boolean signUpUser(SignUpDto signUpDto, boolean isAdmin);

    Boolean assignVolunteer(AssignVolunteer assignVolunteer);

    Boolean addFest(FestDto festDto);

    Boolean addEventToFest(EventDto eventDto);

    Boolean addParticipants(AddParticipantsDto addParticipantsDto);

    Boolean verifyParticipant(ParticipantVerifyReq participantVerifyReq);
    List<Map<String, Instant>> fetchFests();
}
