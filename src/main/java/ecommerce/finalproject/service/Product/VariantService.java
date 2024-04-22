package ecommerce.finalproject.service.Product;

import ecommerce.finalproject.entity.Product;
import ecommerce.finalproject.entity.Variant;
import ecommerce.finalproject.request.CreateVariantForm;

public interface VariantService {
    
    public Variant createVariant(CreateVariantForm request);

    public Variant getVariantById(long variantId);

    public String deleteVariant(Long variantId);

    public String updateVariant(Long variantId, int addedStock);

    public String addSize(Long variantId, String sizeName, int Stock);

    public Variant isVariantExisted(Product product, String size, String color);
}
