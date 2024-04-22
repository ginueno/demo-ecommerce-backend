package ecommerce.finalproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecommerce.finalproject.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    public List<Product> findTop10ByOrderByCreatedAtDesc();

    @Query("SELECT p FROM Product p " +
            "JOIN p.lpages lp " +
            "JOIN lp.parentCategory c " +
            "JOIN p.collections col " +
            "JOIN col.parentCategory c2 " +
            "JOIN p.variants v " +
            "WHERE (:categoryName IS NULL OR c.name = :categoryName OR c2.name = :categoryName) " +
            "AND (:collectionName IS NULL OR col.name = :collectionName) " +
            "AND (:lPageName IS NULL OR lp.name = :lPageName) " +
            "AND (:productName IS NULL OR p.name = :productName) " +
            "AND (:variantSizeName IS NULL OR v.size.name = :variantSizeName) " +
            "AND (:variantColorName IS NULL OR v.color.name = :variantColorName)")
    Page<Product> findAllByCriteria(
            @Param("categoryName") String categoryName,
            @Param("collectionName") String collectionName,
            @Param("lPageName") String lPageName,
            @Param("productName") String productName,
            @Param("variantSizeName") String variantSizeName,
            @Param("variantColorName") String variantColorName,
            Pageable pageable);
}
