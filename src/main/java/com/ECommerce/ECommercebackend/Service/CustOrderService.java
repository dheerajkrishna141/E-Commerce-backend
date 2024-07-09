package com.ECommerce.ECommercebackend.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ECommerce.ECommercebackend.Entity.CustOrder;
import com.ECommerce.ECommercebackend.Payload.CustOrderDTO;

public interface CustOrderService {

	
	public String createOrder(String username,CustOrderDTO order) throws Exception;

	public Page<CustOrder> getOrders(String username, Integer pageNo, Integer pageSize, String sort);
	public List<CustOrder> getOrders(String username);
	
	public String cancelOrder(String username,List<Long> orderIds) throws Exception;
	
	public String cancelOrder(String username,long orderId, List<Long>quantityIds) throws Exception;
	
	
}
