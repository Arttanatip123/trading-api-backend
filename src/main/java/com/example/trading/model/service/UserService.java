package com.example.trading.model.service;


import com.example.trading.model.beans.LoginBean;
import com.example.trading.model.entities.UserProfile;
import com.example.trading.oauth2.TokenService;
import com.example.trading.util.EncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private EncoderUtil encoderUtil;

    @Autowired
    private TokenService tokenService;

    public Optional<Map<String, Object>> login(LoginBean loginBean) {
        UserProfile userProfile = userProfileRepository.findByUserName(loginBean.getUsername());
        Map<String, Object> ret = new HashMap<>();
        if (userProfile != null) {
            String userPassWord = userProfile.getPassWord();
            if (encoderUtil.passwordEncoder().matches(loginBean.getPassword(), userPassWord)) {
                ret.put("status", 0);
                ret.put("token", tokenService.createToken(userProfile));
                ret.put("userId", userProfile.getIdUserProfile());
                return Optional.of(ret);
            } else {
                ret.put("status", 1);
                return Optional.of(ret);
            }
        }else{
            ret.put("status", 1);
        }
        return Optional.of(ret);
    }
}