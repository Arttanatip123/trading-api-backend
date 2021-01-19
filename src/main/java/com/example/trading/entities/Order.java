package com.example.trading.entities;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_list")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idOrderList;
    private int idUserProfile;
    private String userName;
    private int idUserShop;
    private String shopName;
    private String timeReceive;
    private int totalPrice;
    private String productList;
    private int orderStatus;
}

