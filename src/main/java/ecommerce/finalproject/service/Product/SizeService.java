package ecommerce.finalproject.service.Product;

import java.util.List;

import ecommerce.finalproject.entity.Size;

public interface SizeService {
    public Size createSize(String name);
    public String deleteSize(Long sizeId);
    public List<Size> getAllSize();
}
