package ecommerce.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecommerce.finalproject.entity.Rating;

public interface RatingRepo extends JpaRepository<Rating, Long>{
    
    @Query("select r from Rating r where r.product.id=:productId")
    public List<Rating> getProductRatings(@Param("productId")Long productId);
}
