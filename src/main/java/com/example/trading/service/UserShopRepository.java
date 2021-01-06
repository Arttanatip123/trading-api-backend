package com.example.trading.service;

import com.example.trading.entities.UserShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserShopRepository extends JpaRepository<UserShop, Integer> {


}
