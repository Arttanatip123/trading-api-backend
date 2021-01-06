package com.example.trading.entities;
import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "product_list")
public class Product {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int idProduct;
    private int idUserShop;
    private String productName;
    private int productPrice;
    private int productAmount;
    private String productType;
    private String productSubType;
    private String productImg;

}
