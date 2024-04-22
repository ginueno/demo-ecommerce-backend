package ecommerce.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.finalproject.entity.Size;

public interface SizeRepo extends JpaRepository<Size, Long> {
    public Size findByName(String name);
}
