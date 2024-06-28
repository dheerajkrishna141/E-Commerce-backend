package com.ECommerce.ECommercebackend.Service;

import com.ECommerce.ECommercebackend.Payload.AdminOrderUpdateDTO;

public interface AdminOrderService {
	
	public String orderUpdate(AdminOrderUpdateDTO orderDTO) throws Exception;


}
