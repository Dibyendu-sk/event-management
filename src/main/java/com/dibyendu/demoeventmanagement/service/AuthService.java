package com.dibyendu.demoeventmanagement.service;

import com.dibyendu.demoeventmanagement.models.UserDetailsModel;
import com.dibyendu.demoeventmanagement.models.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    public UserInfo getLoggedInUserDtls() {
        // Fetch the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsModel) {
                // Get email (username) from the UserDetails object
                UserInfo userInfo = new UserInfo();
                userInfo.setEmail(((UserDetailsModel) principal).getEmail());
                userInfo.setAssignedEvents(((UserDetailsModel) principal).getAssignedEvents());
                return userInfo;
            } else {
                // In case the principal is just a string (e.g., anonymous or simple login)
                return null;
            }
        }
        return null;
    }
}
