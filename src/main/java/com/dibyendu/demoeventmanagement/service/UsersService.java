package com.dibyendu.demoeventmanagement.service;

import com.dibyendu.demoeventmanagement.models.*;
import com.dibyendu.demoeventmanagement.models.documents.Participants;
import com.dibyendu.demoeventmanagement.models.entity.Events;
import jakarta.transaction.UserTransaction;

import java.util.List;
import java.util.Set;

public interface UsersService {
    Boolean signUpUser(SignUpDto signUpDto, boolean isAdmin);

    Boolean assignVolunteer(AssignVolunteer assignVolunteer);

    Boolean addFest(FestCreateDto festCreateDto);

    Boolean addEventToFest(EventAddDto eventAddDto);

    Boolean addParticipants(AddParticipantsDto addParticipantsDto);

    Boolean verifyParticipant(ParticipantVerifyReq participantVerifyReq);
    List<FestDto> fetchFests();
    Set<Events> fetchEvents(String festId);
    Set<Participants> fetchParticipants(String festId);
}
