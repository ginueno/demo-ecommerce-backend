package ecommerce.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecommerce.finalproject.entity.Cart;

public interface CartRepo extends JpaRepository<Cart, Long>{
    
    @Query("select c from Cart c where c.user.id=:userId")
    public Cart findByUserId(@Param("userId")Long userId);
}
