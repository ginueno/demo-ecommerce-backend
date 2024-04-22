package ecommerce.finalproject.service.Product;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ecommerce.finalproject.entity.Color;
import ecommerce.finalproject.entity.Product;
import ecommerce.finalproject.exception.Product.ProductException;
import ecommerce.finalproject.filter.ProductFilterForm;
import ecommerce.finalproject.request.CreateProductRequest;

public interface ProductService {

    public Product createProduct(CreateProductRequest request);

    public Product findProductById(Long productId) throws ProductException;

    public String deleteProduct(Long productId) throws ProductException;

    public String addToCollection(Long productId, List<Long> collectionId) throws ProductException;

    public Page<Product> getAllProduct(ProductFilterForm form, Pageable pageable) throws ProductException;

    public Map<String, List<String>> getAllColorAndSize(Long productId) throws ProductException;

    public String addToLPage(Long productId, List<Long> lpageId) throws ProductException;

    public List<Product> getRecentProduct();

    public Product updatePrice(Long productId, int price, int discountedPrice) throws ProductException;
    
}
