package com.ECommerce.ECommercebackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustOrderQuantities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "productId", nullable = false)
	private Product product;
	
	@Column(name="quantity", nullable = false)
	private Integer quantity;
	
	@JsonProperty(access = Access.READ_ONLY)
	private Double qprice;
	
	@JsonIgnore
	@ManyToOne(optional =false)
	@JoinColumn(name="orderId", nullable = false)
	private CustOrder order;
	
}
