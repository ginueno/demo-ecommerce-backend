package ecommerce.finalproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.finalproject.entity.Color;
import ecommerce.finalproject.entity.Product;
import ecommerce.finalproject.entity.Size;
import ecommerce.finalproject.entity.Variant;
import ecommerce.finalproject.exception.Product.ProductException;
import ecommerce.finalproject.request.CreateProductRequest;
import ecommerce.finalproject.request.CreateVariantForm;
import ecommerce.finalproject.response.ApiResponse;
import ecommerce.finalproject.service.Product.ColorService;
import ecommerce.finalproject.service.Product.ProductService;
import ecommerce.finalproject.service.Product.SizeService;
import ecommerce.finalproject.service.Product.VariantService;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private VariantService variantService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SizeService sizeService;

    @PostMapping("/")
    public ResponseEntity<Product> createProductHandler(@RequestBody CreateProductRequest req) throws ProductException {
        Product product = productService.createProduct(req);
        return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProductHandler(@PathVariable Long productId) throws ProductException {
        System.out.println("delete product controller .... ");
        String msg = productService.deleteProduct(productId);
        System.out.println("dlete product controller .... msg " + msg);
        ApiResponse apiResponse = new ApiResponse(msg, true);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{productId}/update/collection")
    public ResponseEntity<ApiResponse> addProductToCollectionHandler(@PathVariable Long productId,
            @RequestBody List<Long> collectionId) throws ProductException {
        Product product = productService.findProductById(productId);
        String msg = productService.addToCollection(productId, collectionId);
        ApiResponse apiResponse = new ApiResponse(msg, true);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{productId}/update/lpage")
    public ResponseEntity<ApiResponse> addProductToLPageHandler(@PathVariable Long productId,
            @RequestBody List<Long> lPageId) throws ProductException {
        Product product = productService.findProductById(productId);
        String msg = productService.addToLPage(productId, lPageId);
        ApiResponse apiResponse = new ApiResponse(msg, true);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{productId}/update/price")
    public ResponseEntity<Product> updatePriceHandler(@PathVariable Long productId, @RequestParam int price,
            @RequestParam int discountedPrice) throws ProductException {
        Product product = productService.updatePrice(productId, price, discountedPrice);
        return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{productId}/get-size-color")
    public ResponseEntity<Map<String, List<String>>> getColorAndSizehandler(@PathVariable Long productId) throws ProductException {
        Map<String, List<String>> ans = productService.getAllColorAndSize(productId);
        return new ResponseEntity<>(ans, HttpStatus.ACCEPTED);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Product>> recentlyProductHandler() {
        List<Product> products = productService.getRecentProduct();
        return new ResponseEntity<>(products, HttpStatus.ACCEPTED);
    }

    @PostMapping("/color")
    public ResponseEntity<Color> createColorHandler(@RequestParam String name, @RequestParam String code)
            throws ProductException {
        Color newColor = colorService.createColor(name, code);
        return new ResponseEntity<Color>(newColor, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/color/{colorId}")
    public ResponseEntity<ApiResponse> deleteColorHandler(@PathVariable Long colorId) {
        String msg = colorService.deleteColor(colorId);
        ApiResponse apiResponse = new ApiResponse(msg, true);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/color")
    public ResponseEntity<List<Color>> getAllColor() {
        List<Color> colors = colorService.getAllColor();
        return new ResponseEntity<>(colors, HttpStatus.ACCEPTED);
    }

    @PostMapping("/size")
    public ResponseEntity<Size> createSizeHandler(@RequestParam String sizeName) throws ProductException {
        Size newSize = sizeService.createSize(sizeName);
        return new ResponseEntity<Size>(newSize, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/size/{sizeId}")
    public ResponseEntity<ApiResponse> deleteSizeHandler(@PathVariable Long sizeId) {
        String msg = sizeService.deleteSize(sizeId);
        ApiResponse apiResponse = new ApiResponse(msg, true);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/size")
    public ResponseEntity<List<Size>> getAllSize() {
        List<Size> sizes = sizeService.getAllSize();
        return new ResponseEntity<>(sizes, HttpStatus.ACCEPTED);
    }

    @PostMapping("/variant")
    public ResponseEntity<Variant> createVariantHandler(@RequestBody CreateVariantForm request)
            throws ProductException {
        Variant variant = variantService.createVariant(request);
        return new ResponseEntity<Variant>(variant, HttpStatus.ACCEPTED);
    }

    @GetMapping("/variant/{variantId}")
    public ResponseEntity<Variant> getVariantById(@PathVariable Long variantId) throws ProductException {
        Variant variant = variantService.getVariantById(variantId);
        return new ResponseEntity<Variant>(variant, HttpStatus.ACCEPTED);
    }

    @PutMapping("/variant/{variantId}")
    public ResponseEntity<ApiResponse> addStockToVariant(@PathVariable Long variantId, @RequestParam int addedStock)
            throws ProductException {
        String msg = variantService.updateVariant(variantId, addedStock);
        ApiResponse apiResponse = new ApiResponse(msg, true);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/variant/{variantId}")
    public ResponseEntity<ApiResponse> deleteVariantHandler(@PathVariable Long variantId) throws ProductException {
        String msg = variantService.deleteVariant(variantId);
        ApiResponse apiResponse = new ApiResponse(msg, true);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/variant/{variantId}/addsize")
    public ResponseEntity<ApiResponse> addSizeToColorHandler(@RequestParam String sizeName, @RequestParam int stock,
            @PathVariable Long variantId) {
        String msg = variantService.addSize(variantId, sizeName, stock);
        ApiResponse apiResponse = new ApiResponse(msg, true);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.ACCEPTED);
    }
}
