package ecommerce.finalproject.service.Product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.entity.Color;
import ecommerce.finalproject.entity.Product;
import ecommerce.finalproject.entity.Size;
import ecommerce.finalproject.entity.Variant;
import ecommerce.finalproject.repository.ColorRepo;
import ecommerce.finalproject.repository.ProductRepo;
import ecommerce.finalproject.repository.SizeRepo;
import ecommerce.finalproject.repository.VariantRepository;
import ecommerce.finalproject.request.CreateVariantForm;

@Service
public class VariantServiceImpl implements VariantService {

    @Autowired
    VariantRepository variantRepository;

    @Autowired
    SizeRepo sizeRepo;

    @Autowired
    ColorRepo colorRepo;

    @Autowired
    ProductRepo productRepo;

    @Override
    public Variant createVariant(CreateVariantForm request) {
        Product product = productRepo.findById(request.getProduct_id()).get();
        Size size = sizeRepo.findByName(request.getSize());
        if (size == null) {
            size = new Size();
            size.setName(request.getSize());
            sizeRepo.save(size);
        }
        Color color = colorRepo.findByName(request.getColor());
        if (color == null) {
            color = new Color();
            color.setName(request.getColor());
            color.setCode(request.getColorCode());
            colorRepo.save(color);
        }
        Variant variant = Variant.builder().imgUrls(request.getImgUrls()).color(color).size(size).product(product)
                .stock(request.getStock()).build();
        variantRepository.save(variant);
        return variant;
    }

    @Override
    public String deleteVariant(Long variantId) {
        Variant variant = variantRepository.findById(variantId).get();
        if (variant == null) {
            return "Không tồn tại";
        }
        variantRepository.delete(variant);
        return "Xóa thành công";
    }

    @Override
    public String updateVariant(Long variantId, int addedStock) {
        Variant variant = variantRepository.findById(variantId).get();
        if (variant == null) {
            return "Không tồn tại";
        }
        variant.setStock(variant.getStock() + addedStock);
        return "Thêm hàng thành công";
    }

    @Override
    public String addSize(Long variantId, String sizeName, int Stock) {
        Variant variant = variantRepository.findById(variantId).get();
        if (variant == null) {
            return "Không tồn tại";
        }
        Size size = sizeRepo.findByName(sizeName);
        if (size == null) {
            size = new Size();
            size.setName(sizeName);
            sizeRepo.save(size);
        }
        List<String> imgUrls = new ArrayList<>();
        for (String url : variant.getImgUrls()) {
            imgUrls.add(url);
        }
        Variant newVariant = Variant.builder().color(variant.getColor()).product(variant.getProduct()).size(size)
                .stock(Stock).imgUrls(imgUrls).build();
        variantRepository.save(newVariant);
        return "Thêm size thành công";
    }

    @Override
    public Variant isVariantExisted(Product product, String size, String color) {
        Variant variant = variantRepository.isVariantExisted(product, size, color);
        return variant;
    }

    @Override
    public Variant getVariantById(long variantId) {
        Variant variant = variantRepository.findById(variantId).get();
        if (variant.getProductId() == null)
            variant.setProductId(variant.getProduct().getId());
        if (variant.getProductName() == null)
            variant.setProductName(variant.getProduct().getName());
        return variantRepository.save(variant);
    }

}
