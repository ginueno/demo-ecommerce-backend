package ecommerce.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.finalproject.entity.Collection;

public interface CollectionRepo extends JpaRepository<Collection, Long> {
    
    public Collection findByName(String name);
}
