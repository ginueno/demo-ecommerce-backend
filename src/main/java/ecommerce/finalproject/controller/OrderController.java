package ecommerce.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.finalproject.entity.Address;
import ecommerce.finalproject.entity.Order;
import ecommerce.finalproject.entity.OrderStatus;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.OrderException;
import ecommerce.finalproject.exception.User.UserException;
import ecommerce.finalproject.service.Order.OrderService;
import ecommerce.finalproject.service.User.UserService2;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService2 userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrderHandler(@RequestBody Address shippingAddress,
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findProfileByJwt(jwt);
        Order order = orderService.createOrder(user, shippingAddress);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistoryHandler(@RequestHeader("Authorization") String jwt)
            throws OrderException, UserException {
        User user = userService.findProfileByJwt(jwt);
        List<Order> orders = orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findOrderHandler(@PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findProfileByJwt(jwt);
        Order order = orderService.findById(orderId);
        if ((!user.getId().equals(order.getUser().getId()) || order.getOrderStatus().equals(OrderStatus.CANCELLED))
                && !user.getRole().equals("ADMIN")) {
            order = new Order();
        }
        return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
    }

    @PutMapping("/place/{orderId}")
    public ResponseEntity<Order> placeOrderHandler(@PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findProfileByJwt(jwt);
        Order order = orderService.findById(orderId);
        if (order.getUser().getId() == user.getId()) {
            order = orderService.placedOrder(orderId);
        }
        return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
    }

    @PutMapping("/confirm/{orderId}")
    public ResponseEntity<Order> confirmOrderHandler(@PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findProfileByJwt(jwt);
        Order order = orderService.findById(orderId);
        order = orderService.confirmOrder(orderId);
        return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
    }

    @PutMapping("/ship/{orderId}")
    public ResponseEntity<Order> shipOrderHandler(@PathVariable Long orderId, @RequestParam String deliveryDate,
            @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findProfileByJwt(jwt);
        Order order = orderService.findById(orderId);
        order = orderService.shippedOrder(orderId, deliveryDate);
        return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
    }

    @PutMapping("/deliver/{orderId}")
    public ResponseEntity<Order> deliverOrderHandler(@PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findProfileByJwt(jwt);
        Order order = orderService.findById(orderId);
        order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<Order> cancelOrderHandler(@PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findProfileByJwt(jwt);
        Order order = orderService.findById(orderId);
        if (user.getId().equals(order.getUser().getId())) {
            order = orderService.canceledOrder(orderId);
        }
        return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
    }
}
