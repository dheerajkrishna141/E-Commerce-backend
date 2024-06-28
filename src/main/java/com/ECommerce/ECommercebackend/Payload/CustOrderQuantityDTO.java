package com.ECommerce.ECommercebackend.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustOrderQuantityDTO {
	
	private Integer quantity;
	private long productId;

}
