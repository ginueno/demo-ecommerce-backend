package ecommerce.finalproject.service.Cart;

import ecommerce.finalproject.entity.Cart;
import ecommerce.finalproject.entity.CartItem;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.User.UserException;
import ecommerce.finalproject.request.AddItemToCartRequest;

public interface CartService {
    
    public Cart createCart(User user);

    public CartItem addCartItem(Long userId, AddItemToCartRequest request);

    public Cart findUserCart(Long userId) throws UserException;

}
