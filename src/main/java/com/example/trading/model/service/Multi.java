package com.example.trading.model.service;

import com.example.trading.model.entities.UserProfile;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

public class Multi extends Thread {
    APIResponse res = new  APIResponse();
    UserProfile userProfile = new UserProfile();

    @Autowired
    UserProfileRepository userProfileRepository;


    public void run(){
        System.out.println("Thread running...");

    }
}
