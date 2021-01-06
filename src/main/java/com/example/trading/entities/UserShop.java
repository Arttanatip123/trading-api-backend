package com.example.trading.entities;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "user_shop")
public class UserShop {
    @Id
    private int idUserShop;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "shop_phone")
    private String shopPhone;

    @Column(name = "shop_latitude")
    private String latitude;

    @Column(name = "shop_longtitude")
    private String longtitude;

    @Column(name = "shop_office_hours")
    private String officeHours;

    @Column(name = "shop_comment")
    private String shopComment;

    @Column(name = "shop_img")
    private String shopImg;

    @Column(name = "shop_status")
    private String shopStatus;


}
