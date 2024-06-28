package com.ECommerce.ECommercebackend.Payload;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminOrderUpdateDTO {
	
	private Set<Long> orderIds;
	
	private int statusUpdateCode;

}
