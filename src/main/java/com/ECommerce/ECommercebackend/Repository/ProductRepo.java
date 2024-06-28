package com.ECommerce.ECommercebackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECommerce.ECommercebackend.Entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{

	Product findByName(String name);

}
