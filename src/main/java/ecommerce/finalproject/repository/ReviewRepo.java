package ecommerce.finalproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ecommerce.finalproject.entity.Review;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.product.id=:productId")
    public List<Review> getProductReviews(@Param("productId") Long productId);

    @Query("select r from Review r where r.product.id = :productId and r.user.id = :userId")
    Optional<Review> findReviewByProductIdAndUserId(@Param("productId") Long productId, @Param("userId") Long userId);
}
