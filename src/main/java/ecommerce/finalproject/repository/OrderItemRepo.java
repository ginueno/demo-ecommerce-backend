package ecommerce.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.finalproject.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
    
}
