package ecommerce.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.finalproject.entity.LPage;


public interface LPageRepo extends JpaRepository<LPage, Long>{

    public LPage findByName(String name);
    
}
