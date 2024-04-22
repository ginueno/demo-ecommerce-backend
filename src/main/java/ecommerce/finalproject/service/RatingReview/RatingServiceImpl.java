package ecommerce.finalproject.service.RatingReview;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.entity.Product;
import ecommerce.finalproject.entity.Rating;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.Product.ProductException;
import ecommerce.finalproject.repository.RatingRepo;
import ecommerce.finalproject.request.RatingRequest;
import ecommerce.finalproject.service.Product.ProductService;
import jakarta.transaction.Transactional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepo ratingRepo;

    @Autowired
    ProductService productService;

    @Override
    @Transactional
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        Rating rating = Rating.builder().product(product).rating(req.getRating()).user(user)
                .createdAt(LocalDateTime.now()).build();
        return ratingRepo.save(rating);
    }

    @Override
    public List<Rating> getProductRating(Long productId) throws ProductException {
        return ratingRepo.getProductRatings(productId);
    }

}
