package com.ECommerce.ECommercebackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECommerce.ECommercebackend.Entity.Inventory;

public interface InventoryRepo extends JpaRepository<Inventory, Long>{
	

}
