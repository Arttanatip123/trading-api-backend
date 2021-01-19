package com.example.trading.service;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.trading.entities.UserProfile;
import org.springframework.data.jpa.repository.Query;


public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile findByUserNameAndPassWord(String userName, String passWord);
    UserProfile findByUserName(String userName);


    @Query(value = "select count(*) from user_profile ", nativeQuery = true)
    int countId ();

}

