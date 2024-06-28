package com.ECommerce.ECommercebackend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECommerce.ECommercebackend.Entity.Address;
import com.ECommerce.ECommercebackend.Entity.LocalUser;

public interface AddressRepo extends JpaRepository<Address, Long>{

	public List<Address> findByUser(LocalUser user);
}
