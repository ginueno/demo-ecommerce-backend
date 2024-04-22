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

import ecommerce.finalproject.entity.Review;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.Product.ProductException;
import ecommerce.finalproject.exception.User.UserException;
import ecommerce.finalproject.request.ReviewRequest;
import ecommerce.finalproject.service.RatingReview.ReviewService;
import ecommerce.finalproject.service.User.UserService2;

@RestController
@RequestMapping("")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService2 userService;

    @PostMapping("/api/review/create")
    public ResponseEntity<Review> createReviewHandler(@RequestBody ReviewRequest request,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findProfileByJwt(jwt);
        Review review = reviewService.createReview(request, user);
        return new ResponseEntity<Review>(review, HttpStatus.ACCEPTED);
    }

    @GetMapping("/review/products/{productId}")
    public ResponseEntity<List<Review>> getProductReviewsHandler(@PathVariable Long productId) throws ProductException {
        List<Review> reviews = reviewService.getAllReview(productId);
        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }
}
