package com.ECommerce.ECommercebackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id", nullable = false)
	private long id;
	
	@JsonIgnore
	@OneToOne(optional = false, orphanRemoval = true)
	@JoinColumn(name="productId", nullable = false, unique = true)
	private Product product;
	
	@Column(name="stockQuantity", nullable = false)
	private Integer stockQuantity;
}
