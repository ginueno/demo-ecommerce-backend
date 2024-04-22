package ecommerce.finalproject.service.Order;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.entity.Order;
import ecommerce.finalproject.repository.OrderRepo;

@Service
public class OrderScheduleDelete {
    @Autowired
    OrderRepo orderRepo;

    @Scheduled(cron = "0 0 * * * ?")
    public void deletePendingOrCancelledOrder() {
        LocalDateTime threshold = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
        List<Order> deleteOrder = orderRepo.findScheduleDeleteOrder(threshold);

        for (Order order : deleteOrder) {
            orderRepo.deleteById(order.getId());
        }
    }
}
