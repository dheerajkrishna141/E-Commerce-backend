package com.ECommerce.ECommercebackend.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	@Id
	@Column(name="id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name", nullable = false, unique =true)
	private String name;
	
	@Column(name="price", nullable = false)
	private Double price;
	
	
	@Column(name="longDescription", nullable = false, length=1000)
	private String longDescription;
	
	@Column(name="shortDescription", nullable = false, length=500)
	private String shortDescription;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToOne(orphanRemoval = true,mappedBy = "product",cascade = CascadeType.REMOVE)
	private Inventory inventory;
	
}