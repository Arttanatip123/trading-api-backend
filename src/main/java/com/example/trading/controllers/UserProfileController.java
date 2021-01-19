package com.example.trading.controllers;
import com.example.trading.entities.UserProfile;
import com.example.trading.entities.UserShop;
import com.example.trading.service.APIResponse;
import com.example.trading.service.UserProfileRepository;
import com.example.trading.service.UserShopRepository;
import com.sun.xml.bind.v2.TODO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserProfileController {
    int idCount;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserShopRepository userShopRepository;


    @GetMapping("/save")
    public Object save(UserProfile userProfile) {
        userProfileRepository.save(userProfile);
        return "OK";
    }

    @GetMapping("/list")
    public Object list() {

        return userProfileRepository.findAll();

    }

    @GetMapping("/detail/{userId}")
    public Object detail(@PathVariable int userId){

        return userProfileRepository.findById(userId).get();
    }

    @PostMapping("/user_detail")
    public Object userDetail(@RequestParam int idUserProfile){
        return userProfileRepository.findById(idUserProfile).get();
    }


    @PostMapping("/login")
    public Object login(@RequestParam String userName, @RequestParam String passWord) {
        APIResponse res = new APIResponse();
        UserProfile userProfile = userProfileRepository.findByUserNameAndPassWord(userName,passWord);
        System.out.println(userProfile);
        if (userProfile != null) {
            res.setUserId(userProfile.getIdUserProfile());
            res.setStatus(0);
            res.setMsg("Login success");
        } else {
            res.setStatus(1);
            res.setMsg("Login fail");
        }
        return res;
    }

    @PostMapping("/login1")
    public Object login1(@RequestParam String userName, @RequestParam String passWord) {
        String token = "";
        Map response = new  HashMap();

        UserProfile userProfile = userProfileRepository.findByUserNameAndPassWord(userName,passWord);
        System.out.println(userProfile);

        if (userProfile != null) {
            token = "UgLGpUGhLIKyiRyGjKIYRvnRJFrOLyirEGFhMVKjGIYdUKvKJGhUEsdTy";
            response.put("status", 0);
            response.put("userId", userProfile.getIdUserProfile());

        } else {
            token = "";
            response.put("status", 1);
        }
        response.put("token",token);
        return response;
    }

    @PostMapping("/register")
    public Object registor(UserProfile userProfile) {
        APIResponse res = new APIResponse();
        UserShop userShop = new UserShop();
        UserProfile userProfileDb = userProfileRepository.findByUserName(userProfile.getUserName());    //ค้นหายูเซอร์ด้วยชื่อ
        System.out.println(userProfileDb);
        if (userProfileDb == null) {                           //หากยูเซอร์ที่ตรวจสอบแล้วไม่ซ้ำ
            idCount = userProfileRepository.countId();          //ตรวจสอบจำนวน id ที่มี
            System.out.println(idCount);
            idCount = idCount + 1;                              //นำไอดีที่มีบวก 1
            userProfile.setIdUserProfile(idCount);                  //เซ็ตไอดีกลังจากบวกแล้ว
            userProfile = userProfileRepository.save(userProfile);      //บันทึกยูเซอร์
            res.setData(userProfile);
            userShop.setIdUserShop(userProfile.getIdUserProfile());     //เซ็ตไอดีให้ช็อปจากการดึงไอดีของยูเซอร์
            userShop = userShopRepository.save(userShop);       //บันทึกช็อป
            res.setUserId(userProfile.getIdUserProfile());      //เซ็ตไอดีที่บันทึกแล้ว เพื่อตอบกลับ
            res.setStatus(0);
            res.setMsg("Register success");

        } else {
            res.setStatus(1);
            res.setMsg("Username exist");
        }
        return res;
    }

    @GetMapping("/count")
    public Object count(){
        int count = userProfileRepository.countId();
        return count;
    }
}

