package ecommerce.finalproject.service.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.entity.Cart;
import ecommerce.finalproject.entity.CartItem;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.entity.Variant;
import ecommerce.finalproject.exception.User.UserException;
import ecommerce.finalproject.repository.CartRepo;
import ecommerce.finalproject.repository.VariantRepository;
import ecommerce.finalproject.request.AddItemToCartRequest;
import ecommerce.finalproject.service.Product.ProductService;
import ecommerce.finalproject.service.User.UserService2;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    ProductService productService;

    @Autowired
    VariantRepository variantRepository;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    UserService2 userService;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepo.save(cart);
    }

    @Override
    public CartItem addCartItem(Long userId, AddItemToCartRequest request) {
        Cart cart = cartRepo.findByUserId(userId);
        Variant variant = variantRepository.findById(request.getVariantId()).get();

        CartItem isPresent = cartItemService.isCartItemExisted(cart, variant, userId);

        if (isPresent == null) {
            CartItem cartItem = CartItem.builder().cart(cart).quantity(request.getQuantity()).userId(userId).variant(variant).build();
            cartItem.setPrice(variant.getProduct().getPrice() * cartItem.getQuantity());
            cartItem.setDiscountedPrice(variant.getProduct().getDiscountedPrice() * cartItem.getQuantity());
            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
            cartRepo.save(cart);
            return createdCartItem;
        }
        
        return isPresent;
    }

    @Override
    public Cart findUserCart(Long userId) throws UserException {
        Cart cart = cartRepo.findByUserId(userId);
        if(cart == null) {
            User user = userService.findById(userId);
            cart = createCart(user);
        }

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice = totalPrice + cartItem.getPrice();
            totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
            totalItem = totalItem + cartItem.getQuantity();
        }

        cart.setTotalItem(totalItem);
        cart.setDiscountedPrice(totalDiscountedPrice);
        cart.setTotalPrice(totalPrice);
        cart.setDiscountAmount(totalPrice - totalDiscountedPrice);

        return cartRepo.save(cart);
    }

}
