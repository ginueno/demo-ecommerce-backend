package ecommerce.finalproject.service.Cart;

import ecommerce.finalproject.entity.Cart;
import ecommerce.finalproject.entity.CartItem;
import ecommerce.finalproject.entity.Variant;
import ecommerce.finalproject.exception.CartItemException;
import ecommerce.finalproject.exception.User.UserException;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id, int quantity) throws CartItemException, UserException;
    
    public CartItem isCartItemExisted(Cart cart, Variant variant, Long userId);

    public void removeCartItem(Long userId, Long CartItemId) throws CartItemException, UserException;

    public CartItem findById(Long cartItemId) throws CartItemException;
}