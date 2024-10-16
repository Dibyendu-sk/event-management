package com.dibyendu.demoeventmanagement.service.impl;

import com.dibyendu.demoeventmanagement.exceptions.ResourceAlreadyExistsException;
import com.dibyendu.demoeventmanagement.models.EventsEnums;
import com.dibyendu.demoeventmanagement.models.Roles;
import com.dibyendu.demoeventmanagement.models.SignUpDto;
import com.dibyendu.demoeventmanagement.models.entity.UserInfo;
import com.dibyendu.demoeventmanagement.repo.UserRepo;
import com.dibyendu.demoeventmanagement.service.UsersService;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.Recipient;
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
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    Set<String> events = Set.of(EventsEnums.GATE1.name(), EventsEnums.GATE2.name(), EventsEnums.HALL.name(), EventsEnums.LUNCH.name());

    @Autowired
    public UsersServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Boolean signUpUser(SignUpDto signUpDto, boolean isAdmin) {
        checkDuplicateUser(signUpDto.getEmail());
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(signUpDto.getEmail());
        userInfo.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        if (isAdmin) {
            userInfo.setRole(Roles.ADMIN.name());
            userInfo.setAssociatedEvent(EventsEnums.ALL.name());
        } else {
            String eventName = signUpDto.getAssociatedEvent().toUpperCase();
            if (events.contains(eventName)) {
                userInfo.setAssociatedEvent(eventName);
                userInfo.setRole(Roles.VOLUNTEER.name());
            } else {
                throw new RuntimeException("Please provide a valid event");
            }
        }
        UserInfo savedUser = userRepo.save(userInfo);
//        sendEmail(savedUser.getEmail(), signUpDto.getPassword());
        return Boolean.TRUE;
    }

    private boolean sendEmail(String receiverMail, String password) {

        Email email = new Email();

        email.setFrom("name", "dibyendu01kar@gmail.com");
        email.addRecipient("name", receiverMail);

        email.setSubject("Log in Credentials");

        email.setPlain("============\nUsername - "+receiverMail+"\npassword - "+password);
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
        if (userRepo.existsByEmail(email)){
            throw new ResourceAlreadyExistsException("Email is already taken.");
        }
    }
}
