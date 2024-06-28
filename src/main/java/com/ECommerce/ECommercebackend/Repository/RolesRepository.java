package com.ECommerce.ECommercebackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECommerce.ECommercebackend.Entity.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {

	Roles findByName(String r);

}
