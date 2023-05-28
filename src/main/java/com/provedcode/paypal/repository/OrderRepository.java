package com.provedcode.paypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.provedcode.paypal.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    Order findByPaypalOrderId(String paypalOrderId);
}
