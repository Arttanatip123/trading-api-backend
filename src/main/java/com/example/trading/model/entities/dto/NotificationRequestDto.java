package com.example.trading.model.entities.dto;

import lombok.Data;

@Data
public class NotificationRequestDto {

    private String target;
    private String title;
    private String body;
}
