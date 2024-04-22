package ecommerce.finalproject.service.Product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.entity.Category;
import ecommerce.finalproject.repository.CategoryRepo;
import ecommerce.finalproject.request.CreateCategoryRequest;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public Category createCategory(CreateCategoryRequest request) {
        Category category = Category.builder().name(request.getName()).categoryImg(request.getBannerImg()).build();
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(Long categoryId, CreateCategoryRequest request) {
        Category category = isExisted(categoryId);
        if(category != null) {
            category.setCategoryImg(request.getBannerImg());
            category.setName(request.getName());
            return categoryRepo.save(category);
        }

        return null;
    }

    @Override
    public Category isExisted(Long categoryId) {
        Optional<Category> ans = categoryRepo.findById(categoryId);
        return ans.get();
    }
    
}
