package com.ECommerce.ECommercebackend.Data;

import java.util.HashMap;

public interface OrderData {

	public static final HashMap<Integer, String> OrderStatus = new HashMap<Integer, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put(0, "PLACED");
		put(1, "SHIPPED");
		put(2, "OUT FOR DELIVERY");
		put(3, "DELIVERED");
		put(4, "CANCELLED");
		put(5, "RETURNED");
		put(6, "DELIVERY FAILED");
	}};
}
