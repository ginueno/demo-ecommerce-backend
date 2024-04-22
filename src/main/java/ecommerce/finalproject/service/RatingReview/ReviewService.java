package ecommerce.finalproject.service.RatingReview;

import java.util.List;

import ecommerce.finalproject.entity.Review;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.Product.ProductException;
import ecommerce.finalproject.request.ReviewRequest;

public interface ReviewService {
    public Review createReview(ReviewRequest request, User user) throws ProductException;
    public List<Review> getAllReview(Long productId) throws ProductException;
}
