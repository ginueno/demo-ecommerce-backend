package ecommerce.finalproject.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecommerce.finalproject.entity.Order;
import ecommerce.finalproject.entity.OrderStatus;

public interface OrderRepo extends JpaRepository<Order, Long> {
    public List<Order> findAllByOrderByCreatedAtDesc();

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = PLACED OR o.orderStatus = CONFIRMED OR o.orderStatus = SHIPPED OR o.orderStatus = DELIVERED)")
    public List<Order> getUserOrders(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o WHERE (o.orderStatus = PENDING OR o.orderStatus = CANCELLED) AND o.createdAt <= :date")
    List<Order> findScheduleDeleteOrder(@Param("date") LocalDateTime date);
}
