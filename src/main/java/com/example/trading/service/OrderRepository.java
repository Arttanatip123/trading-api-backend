package com.example.trading.service;

import com.example.trading.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByIdUserProfile(int idUserProfile);
    List<Order> findAllByIdUserShop(int idUserShop);
}
