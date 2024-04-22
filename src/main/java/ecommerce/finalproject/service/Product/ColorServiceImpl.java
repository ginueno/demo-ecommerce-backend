package ecommerce.finalproject.service.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.entity.Color;
import ecommerce.finalproject.repository.ColorRepo;

@Service
public class ColorServiceImpl implements ColorService{

    @Autowired
    ColorRepo colorRepo;

    @Override
    public Color createColor(String name, String code) {
        Color newColor = Color.builder().name(name).code(code).build();
        return colorRepo.save(newColor);
    }

    @Override
    public String deleteColor(Long colorId) {
        colorRepo.deleteById(colorId);
        return "Delete successfully";
    }

    @Override
    public List<Color> getAllColor() {
        return colorRepo.findAll();
    }
    
}
