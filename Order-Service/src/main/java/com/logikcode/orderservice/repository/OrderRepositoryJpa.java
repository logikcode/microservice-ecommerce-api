package com.logikcode.orderservice.repository;

import com.logikcode.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositoryJpa extends JpaRepository<Order, Long> {

}
