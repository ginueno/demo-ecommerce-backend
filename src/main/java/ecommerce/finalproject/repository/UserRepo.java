package ecommerce.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.finalproject.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    
	public List<User> findAllByOrderByCreateAtDesc();
}
