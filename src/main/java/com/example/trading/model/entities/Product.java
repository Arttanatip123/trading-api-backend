package com.example.trading.model.entities;

import javax.annotation.Generated;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "product_list")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product")
    private int idProduct;
    @Column(name = "id_user_shop")
    private int idUserShop;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_price")
    private int productPrice;
    @Column(name = "product_amount")
    private int productAmount;
    @Column(name = "product_type")
    private String productType;
    @Column(name = "product_sub_type")
    private String productSubType;
    @Column(name = "product_img")
    private String productImg;

}
