package com.ECommerce.ECommercebackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECommerce.ECommercebackend.Entity.CustOrderQuantities;

public interface CustOrderQuantityRepo extends JpaRepository<CustOrderQuantities, Long>{
	

}
