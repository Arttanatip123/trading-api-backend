package com.example.trading.controllers;

import com.example.trading.entities.OrderLIst;
import com.example.trading.service.APIResponse;
import com.example.trading.service.OrderListRepository;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderlist")
public class OrderListController {
    APIResponse res = new APIResponse();

    @Autowired
    private OrderListRepository orderListRepository;

    @GetMapping("/list")
    public Object list(){
        return orderListRepository.findAll();
    }

    @PostMapping("/save")
    public  Object save(OrderLIst orderLIst){
        System.out.println(orderLIst);
        res.setStatus(0);
        return res;
    }
}
