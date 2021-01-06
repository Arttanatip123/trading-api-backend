package com.example.trading.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "order_list")
public class OrderLIst {
    @Id
    private int idOrderList;
    private int idUserShop;
    private int idUserProfile;
    private String timeOut;
}
