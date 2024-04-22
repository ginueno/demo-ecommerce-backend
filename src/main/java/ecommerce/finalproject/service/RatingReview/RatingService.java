package ecommerce.finalproject.service.RatingReview;

import java.util.List;

import ecommerce.finalproject.entity.Rating;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.Product.ProductException;
import ecommerce.finalproject.request.RatingRequest;

public interface RatingService {
    public Rating createRating(RatingRequest req, User user) throws ProductException;
    public List<Rating> getProductRating(Long productId) throws ProductException;
}
