package ecommerce.finalproject.service.RatingReview;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.entity.Product;
import ecommerce.finalproject.entity.Review;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.Product.ProductException;
import ecommerce.finalproject.repository.ProductRepo;
import ecommerce.finalproject.repository.ReviewRepo;
import ecommerce.finalproject.request.ReviewRequest;
import ecommerce.finalproject.service.Product.ProductService;
import jakarta.transaction.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepo reviewRepo;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepo productRepo;

    @Override
    @Transactional
    public Review createReview(ReviewRequest request, User user) throws ProductException {
        Product product = productService.findProductById(request.getProductId());

        Optional<Review> isExisted = reviewRepo.findReviewByProductIdAndUserId(request.getProductId(), user.getId());
        if (isExisted.isPresent()) {
            Review newReview = Review.builder().user(user).product(product).review(request.getReview())
                    .score(request.getScore())
                    .createdAt(LocalDateTime.now()).build();
            return reviewRepo.save(newReview);
        }

        Review updateReview = isExisted.get();
        updateReview.setReview(request.getReview());
        updateReview.setScore(request.getScore());
        return reviewRepo.save(updateReview);
    }

    @Override
    public List<Review> getAllReview(Long productId) throws ProductException {
        return reviewRepo.getProductReviews(productId);
    }

}
