package ecommerce.finalproject.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.finalproject.entity.Product;
import ecommerce.finalproject.entity.Variant;
import ecommerce.finalproject.exception.Product.ProductException;
import ecommerce.finalproject.filter.ProductFilterForm;
import ecommerce.finalproject.repository.VariantRepository;
import ecommerce.finalproject.service.Product.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private VariantRepository variantRepo;

    @GetMapping("/")
    public ResponseEntity<Page<Product>> getProductsByFilterHandler(@RequestParam(required = false) String category,
            @RequestParam(required = false) String collection, @RequestParam(required = false) String lpage,
            @RequestParam(required = false) String colors, @RequestParam(required = false) String sort,
            @RequestParam(required = false) String sizes, @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer pageNumber) throws ProductException {
        if (collection.equals(""))
            collection = null;
        if (category.equals(""))
            category = null;
        if (lpage.equals(""))
            lpage = null;
        if (name.equals(""))
            name = null;
        if (colors.equals(""))
            colors = null;
        if (sizes.equals(""))
            sizes = null;
        if (sort.equals(""))
            sort = null;
        ProductFilterForm form = ProductFilterForm.builder().category(category).collection(collection).lpage(lpage)
                .color(colors).size(sizes).sort(sort).name(name).build();
        Pageable pageable = PageRequest.of(pageNumber - 1, 16);
        Page<Product> res = productService.getAllProduct(form, pageable);
        System.out.println("complete request");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        for (Variant variant : product.getVariants()) {
            if (variant.getProductId() == null)
                variant.setProductId(variant.getProduct().getId());
            if (variant.getProductName() == null)
                variant.setProductName(variant.getProduct().getName());
            variantRepo.save(variant);
        }
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

}
