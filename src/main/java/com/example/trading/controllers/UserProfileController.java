package com.example.trading.controllers;

import com.example.trading.model.entities.UserProfile;
import com.example.trading.model.entities.UserShop;
import com.example.trading.model.service.APIResponse;
import com.example.trading.model.service.UserProfileRepository;
import com.example.trading.model.service.UserShopRepository;
import com.example.trading.oauth2.TokenService;
import com.sun.xml.bind.v2.TODO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.trading.util.EncoderUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserProfileController {
    int idCount;
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserShopRepository userShopRepository;

    @Autowired
    private EncoderUtil encoderUtil;

    @Autowired
    private TokenService tokenService;

    APIResponse res = new APIResponse();

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
        UserProfile userProfileDb = userProfileRepository.findById(idUserProfile).get();
        return userProfileDb;
    }

    @PostMapping("/user_update")
    public Object userUpdate(UserProfile userProfile){
        try {
            userProfile = userProfileRepository.save(userProfile);
            res.setStatus(0);
        }catch (Exception e){
            res.setStatus(1);
        }
        return res;
    }

    @PostMapping("/update_password")
    public  Object passwordUpdate(@RequestParam int idUserProfile, @RequestParam String oldPassword , @RequestParam String newPassword){
        UserProfile userProfile = userProfileRepository.findById(idUserProfile).get();
        Map<String, Object> ret = new HashMap<>();
        if (encoderUtil.passwordEncoder().matches(oldPassword, userProfile.getPassWord())) {   //เช็คว่ารหัสเก่าที่ส่งมาตรงกับที่มีในตารางหรือไม่
            userProfile.setPassWord(newPassword);           //เซ้ตรหัสใหม่ลงในออปเจค
            ret.put("token", tokenService.createToken(userProfile));        //สร้าง token จากออปเจค
            userProfile.setPassWord(encoderUtil.passwordEncoder().encode(userProfile.getPassWord()));   //เข้ารหัสพาส
            userProfile = userProfileRepository.save(userProfile);  //บันทึกลงตาราง

            ret.put("status", 0);
            return Optional.of(ret);
        } else {
           ret.put("status", 1);
            return Optional.of(ret);
        }
    }


    @PostMapping("/login")
    public Object login(@RequestParam String userName, @RequestParam String passWord) {
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
            userProfile.setPassWord(encoderUtil.passwordEncoder().encode(userProfile.getPassWord())); //เข้ารหัส password ก่อนบันทึกลงฐานข้อมูล
            userProfile = userProfileRepository.save(userProfile);      //บันทึกยูเซอร์
            res.setData(userProfile);
            userShop.setIdUserShop(userProfile.getIdUserProfile());     //เซ็ตไอดีให้ช็อปจากการดึงไอดีของยูเซอร์
            userShop.setShopStatus("0");
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

