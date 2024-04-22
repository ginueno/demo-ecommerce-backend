package ecommerce.finalproject.service.Product;

import java.util.List;

import ecommerce.finalproject.entity.Color;

public interface ColorService {
    public Color createColor(String name, String code);
    public String deleteColor(Long colorId);
    public List<Color> getAllColor();
}
