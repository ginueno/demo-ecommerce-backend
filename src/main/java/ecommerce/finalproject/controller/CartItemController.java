package ecommerce.finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.finalproject.entity.CartItem;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.CartItemException;
import ecommerce.finalproject.exception.User.UserException;
import ecommerce.finalproject.request.UpdateCartItemRequest;
import ecommerce.finalproject.response.ApiResponse;
import ecommerce.finalproject.service.Cart.CartItemService;
import ecommerce.finalproject.service.User.UserServiceImpl;

@RestController
@RequestMapping("/api/cart_item")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserServiceImpl userService;

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandler(@PathVariable Long itemId,
            @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.findProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), itemId);

        ApiResponse apiResponse = new ApiResponse("Removed cart item", true);

        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<CartItem> updateCartItemHandler(@PathVariable Long itemId,
            @RequestBody UpdateCartItemRequest form, @RequestHeader("Authorization") String jwt)
            throws CartItemException, UserException {
        User user = userService.findProfileByJwt(jwt);

        CartItem updateCartItem = cartItemService.updateCartItem(user.getId(), itemId, form.getQuantity());

        return new ResponseEntity<>(updateCartItem, HttpStatus.ACCEPTED);
    }
}
