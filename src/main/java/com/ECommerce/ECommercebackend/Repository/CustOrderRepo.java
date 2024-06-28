package com.ECommerce.ECommercebackend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECommerce.ECommercebackend.Entity.CustOrder;
import com.ECommerce.ECommercebackend.Entity.LocalUser;

public interface CustOrderRepo extends JpaRepository<CustOrder, Long>{
	List<CustOrder> findByUser(LocalUser user);


}
