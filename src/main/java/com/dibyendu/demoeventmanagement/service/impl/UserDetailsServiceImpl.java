package com.dibyendu.demoeventmanagement.service.impl;

import com.dibyendu.demoeventmanagement.models.UserDetailsModel;
import com.dibyendu.demoeventmanagement.models.entity.UserInfo;
import com.dibyendu.demoeventmanagement.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> user = userRepo.findByEmail(username);
        return user.map(UserDetailsModel::new).orElseThrow(()->new UsernameNotFoundException("Invalid Username"));
    }
}
