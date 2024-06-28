package com.ECommerce.ECommercebackend.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ECommerce.ECommercebackend.Entity.CustOrder;
import com.ECommerce.ECommercebackend.Entity.LocalUser;

public interface CustOrderRepo extends JpaRepository<CustOrder, Long>{

	Page<CustOrder> findByUser(Pageable pr, LocalUser luser);

	List<CustOrder> findByUser(LocalUser luser);


}
