package ecommerce.finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.finalproject.entity.Cart;
import ecommerce.finalproject.entity.CartItem;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.Product.ProductException;
import ecommerce.finalproject.exception.User.UserException;
import ecommerce.finalproject.request.AddItemToCartRequest;
import ecommerce.finalproject.service.Cart.CartService;
import ecommerce.finalproject.service.User.UserServiceImpl;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/user")
    public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        System.out.println("cart - " + cart.getUser().getEmail());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemToCartRequest req,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findProfileByJwt(jwt);
        CartItem cartItem = cartService.addCartItem(user.getId(), req);

        return new ResponseEntity<>(cartItem, HttpStatus.ACCEPTED);
    }
}
