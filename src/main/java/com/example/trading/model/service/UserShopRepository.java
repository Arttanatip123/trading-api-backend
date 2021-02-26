package com.example.trading.model.service;


import com.example.trading.model.entities.UserShop;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserShopRepository extends JpaRepository<UserShop, Integer> {


    List<UserShop> findByshopNameLike(String value);
}
