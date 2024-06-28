package com.ECommerce.ECommercebackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECommerce.ECommercebackend.Entity.LocalUser;
import com.ECommerce.ECommercebackend.Entity.VerificationToken;

public interface VerificationRepo extends JpaRepository<VerificationToken, Long>{

	VerificationToken findByToken(String token);

	void deleteByUser(LocalUser luser);

}
