package ecommerce.finalproject.service.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.entity.Category;
import ecommerce.finalproject.entity.Collection;
import ecommerce.finalproject.entity.Color;
import ecommerce.finalproject.entity.LPage;
import ecommerce.finalproject.entity.Product;
import ecommerce.finalproject.entity.Variant;
import ecommerce.finalproject.exception.Product.ProductException;
import ecommerce.finalproject.filter.ProductFilterForm;
import ecommerce.finalproject.repository.CollectionRepo;
import ecommerce.finalproject.repository.LPageRepo;
import ecommerce.finalproject.repository.ProductRepo;
import ecommerce.finalproject.request.CreateProductRequest;
import ecommerce.finalproject.service.User.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    UserService userService;

    @Autowired
    CollectionRepo collectionRepo;

    @Autowired
    LPageRepo lPageRepo;

    @Override
    public Product createProduct(CreateProductRequest request) {
        Product product = Product.builder().name(request.getName()).imgUrl(request.getImgUrl())
                .description(request.getDescriptions()).descImgUrl(request.getDescImgUrls()).price(request.getPrice())
                .discountedPrice(request.getDiscountedPice()).subTitle(request.getSubTitle())
                .createdAt(LocalDateTime.now()).build();
        for (String collectionName : request.getCollections()) {
            Collection collection = collectionRepo.findByName(collectionName);
            if (collection != null)
                product.getCollections().add(collection);
        }
        for (String lPageName : request.getLpages()) {
            LPage lPage = lPageRepo.findByName(lPageName);
            if (lPage != null)
                product.getLpages().add(lPage);
        }
        productRepo.save(product);

        return product;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        productRepo.delete(product);
        return "Xóa thành công";
    }

    @Override
    public Page<Product> getAllProduct(ProductFilterForm form, Pageable pageable) {
        if (form.getName() == null && form.getSize() == null && form.getColor() == null &&
                form.getCategory() == null && form.getCollection() == null && form.getLpage() == null
                && form.getSort() == null) {
            // Return all products without applying any filters
            return productRepo.findAll(pageable);
        }
        List<Product> products = productRepo.findAll();
        products = products.stream()
                .filter(product -> product.getVariants().stream()
                        .anyMatch(variant -> (form.getColor() == null
                                || variant.getColor().getName().equals(form.getColor()))
                                && (form.getSize() == null || variant.getSize().getName().equals(form.getSize()))))
                .filter(product -> form.getName() == null || product.getName().contains(form.getName()))
                .filter(product -> form.getCategory() == null || product.getLpages().stream()
                        .anyMatch(lp -> lp.getParentCategory().getName().equals(form.getCategory()))
                        || product.getCollections().stream()
                                .anyMatch(col -> col.getParentCategory().getName().equals(form.getCategory())))
                .filter(product -> form.getCollection() == null
                        || product.getCollections().stream()
                                .anyMatch(col -> col.getName().equals(form.getCollection())))
                .filter(product -> form.getLpage() == null
                        || product.getLpages().stream().anyMatch(lp -> lp.getName().equals(form.getLpage())))
                .collect(Collectors.toList());
        if (form.getSort() != null) {
            switch (form.getSort()) {
                case "newest":
                    products = products.stream()
                            .sorted(Comparator.comparing(Product::getCreatedAt).reversed())
                            .collect(Collectors.toList());
                    break;
                case "price_low":
                    products = products.stream()
                            .sorted(Comparator.comparing(Product::getDiscountedPrice))
                            .collect(Collectors.toList());
                    break;
                case "price_high":
                    products = products.stream()
                            .sorted(Comparator.comparing(Product::getDiscountedPrice).reversed())
                            .collect(Collectors.toList());
                    break;
                default:
                    break;
            }
        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), products.size());
        if (start > end) {
            return Page.empty();
        }
        List<Product> pagedProducts = products.subList(start, end);
        Page<Product> filteredPage = new PageImpl<>(pagedProducts, pageable, products.size());
        return filteredPage;

    }

    @Override
    public String addToCollection(Long productId, List<Long> collectionId) throws ProductException {
        List<Collection> collections = new ArrayList<>();
        for (Long id : collectionId) {
            Collection collection = collectionRepo.findById(id).get();
            if (collection != null)
                collections.add(collection);
        }
        Product product = findProductById(productId);
        if (!collections.isEmpty() && product != null) {
            product.getCollections().addAll(collections);
            productRepo.save(product);
            return "Thêm thành công";
        }
        return "Thêm không thành công";
    }

    @Override
    public String addToLPage(Long productId, List<Long> lPageId) throws ProductException {
        List<LPage> lPages = new ArrayList<>();
        for (Long id : lPageId) {
            LPage lPage = lPageRepo.findById(id).get();
            if (lPage != null)
                lPages.add(lPage);
        }
        Product product = findProductById(productId);
        if (!lPages.isEmpty() && product != null) {
            product.getLpages().addAll(lPages);
            productRepo.save(product);
            return "Thêm thành công";
        }
        return "Thêm không thành công";
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> product = productRepo.findById(productId);
        if (!product.isPresent())
            throw new ProductException("Sản phẩm không tồn tại");
        return product.get();
    }

    @Override
    public Map<String, List<String>> getAllColorAndSize(Long productId) throws ProductException {
        Product product = findProductById(productId);
        Map<String, List<String>> list = new HashMap<>();
        for (Variant variant : product.getVariants()) {
            if (!list.containsKey(variant.getColor().getName())) {
                list.put(variant.getColor().getName(), new ArrayList<>());
            }
            list.get(variant.getColor().getName()).add(variant.getSize().getName());
        }
        return list;
    }

    @Override
    public List<Product> getRecentProduct() {
        return productRepo.findTop10ByOrderByCreatedAtDesc();
    }

    @Override
    public Product updatePrice(Long productId, int price, int discountedPrice) throws ProductException {
        Product product = findProductById(productId);
        product.setPrice(price);
        product.setDiscountedPrice(discountedPrice);
        return productRepo.save(product);
    }

}
