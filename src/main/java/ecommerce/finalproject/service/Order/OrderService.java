package ecommerce.finalproject.service.Order;

import java.util.List;

import ecommerce.finalproject.entity.Address;
import ecommerce.finalproject.entity.Order;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.OrderException;
import ecommerce.finalproject.exception.User.UserException;

public interface OrderService {
    public Order createOrder(User user, Address shippingAddress) throws UserException;

    public Order findById(Long orderId) throws OrderException;

    public List<Order> userOrderHistory(Long userId);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId, String deliveryDate) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order canceledOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrder();

    public void deleteOrder(Long orderId) throws OrderException;

}
