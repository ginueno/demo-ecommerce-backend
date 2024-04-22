package ecommerce.finalproject.service.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.entity.Address;
import ecommerce.finalproject.entity.Cart;
import ecommerce.finalproject.entity.CartItem;
import ecommerce.finalproject.entity.Order;
import ecommerce.finalproject.entity.OrderItem;
import ecommerce.finalproject.entity.OrderStatus;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.OrderException;
import ecommerce.finalproject.exception.User.UserException;
import ecommerce.finalproject.repository.AddressRepo;
import ecommerce.finalproject.repository.CartItemRepo;
import ecommerce.finalproject.repository.CartRepo;
import ecommerce.finalproject.repository.OrderItemRepo;
import ecommerce.finalproject.repository.OrderRepo;
import ecommerce.finalproject.repository.UserRepo;
import ecommerce.finalproject.service.Cart.CartService;
import ecommerce.finalproject.service.Product.ProductService;
import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private CartRepo cartRepo;

    @Override
    @Transactional
    public Order createOrder(User user, Address shippingAddress) throws UserException {
        if (shippingAddress.getId() == null) {
            shippingAddress.setUser(user);
            shippingAddress = addressRepo.save(shippingAddress);
            user.getAddresses().add(shippingAddress);
            userRepo.save(user);
        } else {
            shippingAddress = addressRepo.findById(shippingAddress.getId()).get();
        }

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = OrderItem.builder().price(item.getPrice()).variant(item.getVariant())
                    .quantity(item.getQuantity()).userId(item.getUserId()).discountedPrice(item.getDiscountedPrice())
                    .build();
            OrderItem newOrderItem = orderItemRepo.save(orderItem);

            orderItems.add(newOrderItem);
        }

        Order newOrder = Order.builder().user(user).orderItems(orderItems).totalPrice(cart.getTotalPrice())
                .discountedPrice(cart.getDiscountedPrice()).totalItem(cart.getTotalItem())
                .shippingAddress(shippingAddress)
                .orderDate(LocalDateTime.now()).orderStatus(OrderStatus.PENDING).createdAt(LocalDateTime.now()).build();
        Order saveOrder = orderRepo.save(newOrder);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(saveOrder);
            orderItemRepo.save(orderItem);
        }

        return saveOrder;
    }

    @Override
    public Order findById(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepo.findById(orderId);

        if (opt.isPresent())
            return opt.get();

        throw new OrderException("Order id not existed: " + orderId);
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {
        List<Order> orders = orderRepo.getUserOrders(userId);
        return orders;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findById(orderId);
        // order.getPaymentDetails().setPaymentStatus("COMPLETE");
        order.setOrderStatus(OrderStatus.PLACED);
        Cart cart = cartRepo.findByUserId(order.getUser().getId());
        cart.getCartItems().clear();
        cartRepo.save(cart);
        return orderRepo.save(order);
    }

    @Override
    public Order confirmOrder(Long orderId) throws OrderException {
        Order order = findById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return orderRepo.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId, String deliveryDate) throws OrderException {
        Order order = findById(orderId);
        LocalDateTime deliverTime = LocalDate.parse(deliveryDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                .atStartOfDay();
        order.setOrderStatus(OrderStatus.SHIPPED);
        order.setDeliveryDate(deliverTime);
        return orderRepo.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findById(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setDeliveryDate(LocalDateTime.now());
        return orderRepo.save(order);
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order = findById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepo.save(order);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepo.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findById(orderId);
        orderRepo.delete(order);
    }

}
