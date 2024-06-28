package com.ECommerce.ECommercebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ECommerce.ECommercebackend.Exceptions.OrderNotFoundExcpetion;
import com.ECommerce.ECommercebackend.Payload.AdminOrderUpdateDTO;
import com.ECommerce.ECommercebackend.Service.AdminOrderService;

@RestController
@RequestMapping("/admin/order")
public class AdminOrderController {
	
	@Autowired
	private AdminOrderService orderService;
	
	@PostMapping("/update")
	public ResponseEntity<?> UpdateOrderStatus(@RequestBody AdminOrderUpdateDTO OrderUpdateDTO){
		try {
			return new ResponseEntity<>(orderService.orderUpdate(OrderUpdateDTO), HttpStatus.ACCEPTED);
		} catch (OrderNotFoundExcpetion e) {

			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} 
		catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
