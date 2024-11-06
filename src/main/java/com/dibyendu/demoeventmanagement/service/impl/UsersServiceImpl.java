package com.dibyendu.demoeventmanagement.service.impl;

import com.dibyendu.demoeventmanagement.exceptions.ForbiddenException;
import com.dibyendu.demoeventmanagement.exceptions.ResourceAlreadyExistsException;
import com.dibyendu.demoeventmanagement.exceptions.ResourceNotFoundException;
import com.dibyendu.demoeventmanagement.models.*;
import com.dibyendu.demoeventmanagement.models.entity.Events;
import com.dibyendu.demoeventmanagement.models.entity.Fest;
import com.dibyendu.demoeventmanagement.models.documents.Participants;
import com.dibyendu.demoeventmanagement.models.entity.UserInfo;
import com.dibyendu.demoeventmanagement.repo.jpa.EventRepo;
import com.dibyendu.demoeventmanagement.repo.jpa.FestRepo;
import com.dibyendu.demoeventmanagement.repo.mongo.ParticipantRepo;
import com.dibyendu.demoeventmanagement.repo.jpa.UserRepo;
import com.dibyendu.demoeventmanagement.service.AuthService;
import com.dibyendu.demoeventmanagement.service.UsersService;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {
    private final UserRepo userRepo;
    private final ParticipantRepo participantRepo;
    private final PasswordEncoder passwordEncoder;
    private final FestRepo festRepo;
    private final AuthService authService;
    private final EventRepo eventRepo;
    Set<String> events = Set.of(EventsEnums.GATE1.name(), EventsEnums.GATE2.name(), EventsEnums.HALL.name(), EventsEnums.LUNCH.name());

    @Autowired
    public UsersServiceImpl(UserRepo userRepo, ParticipantRepo participantRepo, PasswordEncoder passwordEncoder, FestRepo festRepo, AuthService authService, EventRepo eventRepo) {
        this.userRepo = userRepo;
        this.participantRepo = participantRepo;
        this.passwordEncoder = passwordEncoder;
        this.festRepo = festRepo;
        this.authService = authService;
        this.eventRepo = eventRepo;
    }

    @Override
    @Transactional
    public Boolean signUpUser(SignUpDto signUpDto, boolean isAdmin) {
        checkDuplicateUser(signUpDto.getEmail());
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(signUpDto.getEmail());
        userInfo.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        userInfo.setAssociatedEvents(new HashSet<>());
        if (isAdmin) {
            userInfo.setRole(Roles.ADMIN.name());
//            userInfo.setAssociatedEvent(EventsEnums.ALL.name());
        } else {
            userInfo.setRole(Roles.VOLUNTEER.name());
        }
        UserInfo savedUser = userRepo.save(userInfo);
        return Boolean.TRUE;
    }

    @Override
    public Boolean assignVolunteer(AssignVolunteer assignVolunteer) {
        Fest fest = festRepo.findById(assignVolunteer.getFestId())
                .orElseThrow(() -> new ResourceNotFoundException("Fest not found"));

        // extracting event ids from events
//        List<String> eventIds = fest.getEvents().stream().map(Events::getId).toList();
        Set<Events> festEvents = fest.getEvents();
        List<String> requestedEventIds = assignVolunteer.getEventIds();
        userRepo.findByEmail(assignVolunteer.getVolMail())
                .ifPresentOrElse(userInfo -> {
                            Set<Events> associatedEvents = userInfo.getAssociatedEvents();
                            if (associatedEvents == null) {
                                log.info("Initializing the associated events list if it's null");
                                associatedEvents = new HashSet<>();
                                userInfo.setAssociatedEvents(associatedEvents);
                            }
                            log.info("Total number of events in the fest is "+festEvents.size());
                            Set<Events> eventsSet = festEvents.stream().filter(event -> requestedEventIds.contains(event.getId())).collect(Collectors.toSet());
                            log.info("Assigning new events set to the volunteer");
                            log.info(eventsSet.size()+" events will be assigned to the volunteer.");
                            associatedEvents.addAll(eventsSet);
                            userRepo.save(userInfo);
                        }, () -> {
                            throw new ResourceNotFoundException("Volunteer not found.");
                        }
                );

        return Boolean.TRUE;
    }

    @Override
    public Boolean addFest(FestDto festDto) {
        Fest fest = new Fest();
        fest.setId(festDto.getFestId());
        fest.setFestName(festDto.getFestName());
        festRepo.save(fest);
        return Boolean.TRUE;
    }

    @Override
    public Boolean addEventToFest(EventDto eventDto) {
        festRepo.findById(eventDto.getFestId()).ifPresentOrElse(
                fest -> {
                    Events events = new Events();
                    String eventId = fest.getId() + "_" + eventDto.getShortForm();
                    if (eventRepo.existsById(eventId)) {
                        throw new ResourceAlreadyExistsException("Event already exist.");
                    }
                    events.setId(eventId);
                    events.setDescription(eventDto.getDescription());
                    events.setEventName(eventDto.getEventName());

                    Set<Events> festEvents = fest.getEvents();
                    if (festEvents == null){
                        festEvents=new HashSet<>();
                        fest.setEvents(festEvents);
                    }
                    festEvents.add(events);
                    festRepo.save(fest);
                    eventRepo.save(events);
                }, () -> {
                    throw new ResourceNotFoundException("Fest not found.");
                });
        return Boolean.TRUE;
    }

    @Override
    public Boolean addParticipants(AddParticipantsDto addParticipantsDto) {
        festRepo.findById(addParticipantsDto.getFestId()).ifPresentOrElse(
                fest -> {
                    List<Participants> participants = addParticipantsDto.getParticipants().stream().map(
                            participant -> {
                                participant.setId(addParticipantsDto.getFestId() + "_" + participant.getId());
                                participant.setFestId(fest.getId());
                                return participant;
                            }
                    ).toList();
                    participantRepo.saveAll(participants);
                }, () -> {
                    throw new ResourceNotFoundException("Fest not found.");
                }
        );

        return true;
    }

    @Override
    public Boolean verifyParticipant(ParticipantVerifyReq participantVerifyReq) {
        Set<String> assignedEvents = authService.getLoggedInUserDtls().getAssignedEvents();
        String enteredBy = authService.getLoggedInUserDtls().getEmail();
        String eventId = participantVerifyReq.getEventId();
        log.info("assigned events size - "+assignedEvents.size());
        if (!assignedEvents.contains(eventId)){
            throw new ForbiddenException("Access denied: You do not have permission to view or modify this event.");
        }
        participantRepo.findByFestIdAndNameAndId(participantVerifyReq.getFestId(), participantVerifyReq.getName(), participantVerifyReq.getReg()).ifPresentOrElse(
                participants -> {
                    List<ParticipatedEventDto> participatedEvents = participants.getParticipatedEvents();
                    if (participatedEvents ==null){
                        participatedEvents = new ArrayList<>();
                        participants.setParticipatedEvents(participatedEvents);
                    }
                    if (!participatedEvents.stream().map(ParticipatedEventDto::getEventId).collect(Collectors.toSet()).contains(eventId)) {
                        participatedEvents.add(new ParticipatedEventDto(eventId, new EntryDetails(enteredBy, Instant.now().getEpochSecond())));
                        participantRepo.save(participants);
                    }else {
                        throw new ResourceAlreadyExistsException("Participant already verified with this event");
                    }

                },() ->{
                    throw new ResourceNotFoundException("Participant not found.");
                }
        );
        return Boolean.TRUE;
    }

    @Override
    public List<Map<String, Instant>> fetchFests() {
        String createdBy = authService.getLoggedInUserDtls().getEmail();
        return festRepo.fetchFestNameAndCreatedDate(createdBy);
    }

    private boolean sendEmail(String receiverMail, String password) {

        Email email = new Email();

        email.setFrom("name", "dibyendu01kar@gmail.com");
        email.addRecipient("name", receiverMail);

        email.setSubject("Log in Credentials");

        email.setPlain("============Username - " + receiverMail + "password - " + password);
//        email.setHtml("Username - "+userName+"<br>password - "+password);

        MailerSend ms = new MailerSend();

        ms.setToken(fetchMailerSendToken());

        try {

            MailerSendResponse response = ms.emails().send(email);
            log.info(response.messageId);
            return true;
        } catch (MailerSendException e) {
            log.error("Error sending mail to the user");
            throw new RuntimeException(e);
        }
    }

    private String fetchMailerSendToken() {
        Path filePath = Paths.get("src/main/resources/mailerSendToken.txt"); // Adjust path as necessary

        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(filePath);

            // First line is the token
            String token = lines.get(0);

            log.info("Token: " + token);
            return token;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void checkDuplicateUser(String email) {
        if (userRepo.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException("Email is already taken.");
        }
    }
}
