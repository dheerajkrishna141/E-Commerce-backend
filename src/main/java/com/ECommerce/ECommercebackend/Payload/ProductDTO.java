package com.ECommerce.ECommercebackend.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private String name;
	private Double price;
	private String shortDescription;
	private String longDescription;
	private Integer quantity;

}
