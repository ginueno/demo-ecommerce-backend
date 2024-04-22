package ecommerce.finalproject.service.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.entity.Size;
import ecommerce.finalproject.repository.SizeRepo;

@Service
public class SizeServiceImp implements SizeService{

    @Autowired
    SizeRepo sizeRepo;

    @Override
    public Size createSize(String name) {
        Size size = Size.builder().name(name).build();
        return sizeRepo.save(size);
    }

    @Override
    public String deleteSize(Long sizeId) {
        sizeRepo.deleteById(sizeId);
        return "Delete Successfully";
    }

    @Override
    public List<Size> getAllSize() {
        return sizeRepo.findAll();
    }
    
}
