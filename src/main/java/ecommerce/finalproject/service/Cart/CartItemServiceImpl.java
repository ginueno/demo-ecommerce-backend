package ecommerce.finalproject.service.Cart;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.entity.Cart;
import ecommerce.finalproject.entity.CartItem;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.entity.Variant;
import ecommerce.finalproject.exception.CartItemException;
import ecommerce.finalproject.exception.User.UserException;
import ecommerce.finalproject.repository.CartItemRepo;
import ecommerce.finalproject.repository.CartRepo;
import ecommerce.finalproject.service.User.UserService2;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private UserService2 userService;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setPrice(cartItem.getVariant().getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getVariant().getProduct().getDiscountedPrice() * cartItem.getQuantity());

        CartItem newCartItem = cartItemRepo.save(cartItem);

        return newCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, int quantity) throws CartItemException, UserException {
        CartItem item = findById(id);
        User user = userService.findById(userId);

        if (user.getId().equals(userId)) {
            item.setQuantity(quantity);
            item.setPrice(item.getQuantity() * item.getVariant().getProduct().getPrice());
            item.setDiscountedPrice(item.getQuantity() * item.getVariant().getProduct().getDiscountedPrice());
        }

        return cartItemRepo.save(item);
    }

    @Override
    public CartItem isCartItemExisted(Cart cart, Variant variant, Long userId) {
        CartItem cartItem = cartItemRepo.isCartItemExisted(cart, variant, userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findById(cartItemId);
        User cartItemUser = userService.findById(cartItem.getUserId());
        User reqUser = userService.findById(userId);
        if (cartItemUser.getId().equals(reqUser.getId())) {
            cartItemRepo.deleteById(cartItemId);;
        } else {
            throw new UserException("Không thể xóa item user khác");
        }
    }

    @Override
    public CartItem findById(Long cartItemId) throws CartItemException {
        Optional<CartItem> cartItem = cartItemRepo.findById(cartItemId);
        if (!cartItem.isPresent())
            throw new CartItemException("Item không tồn tại");
        return cartItem.get();
    }

}
