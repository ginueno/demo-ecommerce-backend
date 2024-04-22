package ecommerce.finalproject.service.Order;

import org.springframework.beans.factory.annotation.Autowired;

import ecommerce.finalproject.entity.OrderItem;
import ecommerce.finalproject.repository.OrderItemRepo;

public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    OrderItemRepo orderItemRepo;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepo.save(orderItem);
    }
    
}
