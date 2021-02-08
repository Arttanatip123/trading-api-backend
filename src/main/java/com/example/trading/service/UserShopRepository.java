package com.example.trading.service;

import com.example.trading.entities.UserShop;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserShopRepository extends JpaRepository<UserShop, Integer> {


    List<UserShop> findByshopNameLike(String value);
}
