package com.ECommerce.ECommercebackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECommerce.ECommercebackend.Entity.LocalUser;

public interface UserRepo extends JpaRepository<LocalUser, Long> {


	LocalUser findByEmail(String email);
	
	

}
