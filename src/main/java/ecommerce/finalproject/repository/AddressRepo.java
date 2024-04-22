package ecommerce.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.finalproject.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {

}
