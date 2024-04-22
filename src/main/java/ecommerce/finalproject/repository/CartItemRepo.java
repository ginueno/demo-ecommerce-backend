package ecommerce.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecommerce.finalproject.entity.Cart;
import ecommerce.finalproject.entity.CartItem;
import ecommerce.finalproject.entity.Variant;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci from CartItem ci WHERE ci.variant = :variant and ci.cart = :cart and ci.userId = :userId")
    public CartItem isCartItemExisted(@Param("cart") Cart cart, @Param("variant") Variant variant,
            @Param("userId") Long userId);
}
