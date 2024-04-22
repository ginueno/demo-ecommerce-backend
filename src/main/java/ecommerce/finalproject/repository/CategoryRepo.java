package ecommerce.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.finalproject.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    
    public Category findByName(String name);

}
