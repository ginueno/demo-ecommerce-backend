package ecommerce.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.finalproject.entity.Rating;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.Product.ProductException;
import ecommerce.finalproject.exception.User.UserException;
import ecommerce.finalproject.request.RatingRequest;
import ecommerce.finalproject.service.RatingReview.RatingService;
import ecommerce.finalproject.service.User.UserServiceImpl;

@RestController
@RequestMapping("")
public class RatingController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RatingService ratingService;

    @PostMapping("/api/rating/create")
    public ResponseEntity<Rating> createRatingHandler(@RequestBody RatingRequest request,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findProfileByJwt(jwt);
        Rating rating = ratingService.createRating(request, user);
        return new ResponseEntity<>(rating, HttpStatus.ACCEPTED);
    }

    @GetMapping("/rating/products/{productId}")
    public ResponseEntity<List<Rating>> getProductRatingHandler(@PathVariable Long productId) throws ProductException {
        List<Rating> ratings = ratingService.getProductRating(productId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}
