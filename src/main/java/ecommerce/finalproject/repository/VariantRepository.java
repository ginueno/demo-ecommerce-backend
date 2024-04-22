package ecommerce.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecommerce.finalproject.entity.Product;
import ecommerce.finalproject.entity.Variant;

public interface VariantRepository extends JpaRepository<Variant, Long> {

    @Query("select v from Variant v where v.product = :product and v.size.name = :size and v.color.name = :color")
    public Variant isVariantExisted(@Param("product") Product product, @Param("size") String size,
            @Param("color") String color);
}
