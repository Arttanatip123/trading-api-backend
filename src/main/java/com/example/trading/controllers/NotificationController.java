package com.example.trading.controllers;

import com.example.trading.model.entities.dto.NotificationRequestDto;
import com.example.trading.model.entities.dto.SubscriptionRequestDto;
import com.example.trading.model.service.NotificationService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/subscribe")
    public void subscribeToTopic(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        notificationService.subscribeToTopic(subscriptionRequestDto);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
        notificationService.unsubscribeFromTopic(subscriptionRequestDto);
    }

    @PostMapping("/token")
    public String sendPnsToDevice(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.sendPnsToDevice(notificationRequestDto);
    }

    @PostMapping("/topic")
    public String sendPnsToTopic(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.sendPnsToTopic(notificationRequestDto);
    }
}
