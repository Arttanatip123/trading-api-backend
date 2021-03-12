package com.example.trading.model.entities;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user_profile")
    private int idUserProfile;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "pass_word")
    private String passWord;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "user_lat")
    private String userLat;
    @Column(name = "user_lng")
    private String userLng;
    @Column(name = "fcm_token")
    private String fcmToken;

}
