package com.ECommerce.ECommercebackend.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustOrder {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;
	
	@ManyToOne( optional = false)
	@JoinColumn(name = "userId", nullable = false)
	private LocalUser user;
	
	@ManyToOne(optional =false)
	@JoinColumn(name="addressId", nullable = false)
	private Address address;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<CustOrderQuantities> orders = new ArrayList<>();
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="total")
	private Double total;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="status", nullable = false)
	private String status= "PLACED";
	
	
	
}
