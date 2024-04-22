package ecommerce.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.finalproject.entity.Color;

public interface ColorRepo extends JpaRepository<Color, Long>{
    public Color findByName(String name);
}
