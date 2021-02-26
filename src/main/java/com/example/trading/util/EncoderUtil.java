package com.example.trading.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncoderUtil {

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
