package ecommerce.finalproject.service.Product;

import ecommerce.finalproject.entity.Category;
import ecommerce.finalproject.request.CreateCategoryRequest;

public interface CategoryService {
    public Category createCategory(CreateCategoryRequest request);

    public Category updateCategory(Long categoryId, CreateCategoryRequest request);

    public Category isExisted(Long categoryId);
}
