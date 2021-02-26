package com.example.trading.model.entities;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_list")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_order_list")
    private int idOrderList;
    @Column(name = "id_user_profile")
    private int idUserProfile;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "id_user_shop")
    private int idUserShop;
    @Column(name = "shop_name")
    private String shopName;
    @Column(name = "time_receive")
    private String timeReceive;
    @Column(name = "total_price")
    private int totalPrice;
    @Column(name = "product_list")
    private String productList;
    @Column(name = "order_status")
    private int orderStatus;
}

