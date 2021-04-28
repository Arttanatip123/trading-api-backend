package com.example.trading.model.service;

import com.example.trading.model.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile findByUserNameAndPassWord(String userName, String passWord);
    UserProfile findByUserName(String userName);



    @Query(value = "select count(*) from user_profile ", nativeQuery = true)
    int countId ();

}

