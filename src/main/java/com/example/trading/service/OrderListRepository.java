package com.example.trading.service;

import com.example.trading.entities.OrderLIst;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderListRepository extends JpaRepository<OrderLIst,Integer> {
}
