package com.example.trading.model.service;


import lombok.Data;

public class Multi extends Thread {

    @Override
    public void run(){
        System.out.println("Thread running...");

    }
}


