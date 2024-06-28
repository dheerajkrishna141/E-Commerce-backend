package com.ECommerce.ECommercebackend.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ECommerce.ECommercebackend.Data.OrderData;
import com.ECommerce.ECommercebackend.Entity.CustOrder;
import com.ECommerce.ECommercebackend.Exceptions.OrderNotFoundExcpetion;
import com.ECommerce.ECommercebackend.Payload.AdminOrderUpdateDTO;
import com.ECommerce.ECommercebackend.Repository.CustOrderRepo;
import com.ECommerce.ECommercebackend.Service.AdminOrderService;

@Service
public class AdminOrderUpdateImpl implements AdminOrderService {

	@Autowired
	private CustOrderRepo orderRepo;

	@Override
	public String orderUpdate(AdminOrderUpdateDTO orderUpdate) throws Exception {
		Set<Long> orderIds = orderUpdate.getOrderIds();
		int statusCode = orderUpdate.getStatusUpdateCode();
		HashMap<Integer, String> orderStatus = OrderData.OrderStatus;
		List<CustOrder> orderList = new ArrayList<>();
		try {
			for (Long id : orderIds) {

				CustOrder order = orderRepo.findById(id)
						.orElseThrow(() -> new OrderNotFoundExcpetion("Order with ID:"+id+" not found!"));
				order.setStatus(orderStatus.get(statusCode));
				orderList.add(order);
				
			}
			orderRepo.saveAll(orderList);
		} catch (OrderNotFoundExcpetion e) {
			throw new OrderNotFoundExcpetion(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "Successfully Updated";
	}

}
