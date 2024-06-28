package com.ECommerce.ECommercebackend.Service;

import java.util.List;
import java.util.Set;

import com.ECommerce.ECommercebackend.Entity.CustOrder;
import com.ECommerce.ECommercebackend.Payload.CustOrderDTO;

public interface CustOrderService {

	
	public String createOrder(CustOrderDTO order, String username) throws Exception;

	public List<CustOrder> getOrders(String username);
	
	public String cancelOrder(String username,List<Long> orderIds) throws Exception;
	
	public String cancelOrder(String username,long orderId, List<Long>quantityIds) throws Exception;
	
	
}
