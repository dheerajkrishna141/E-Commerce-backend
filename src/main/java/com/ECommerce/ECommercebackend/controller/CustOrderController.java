package com.ECommerce.ECommercebackend.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ECommerce.ECommercebackend.Entity.CustOrder;
import com.ECommerce.ECommercebackend.Exceptions.OrderNotFoundExcpetion;
import com.ECommerce.ECommercebackend.Payload.CustOrderDTO;
import com.ECommerce.ECommercebackend.Payload.orderDeleteDTO;
import com.ECommerce.ECommercebackend.Service.CustOrderService;

@RestController
@RequestMapping("/user/orders")
public class CustOrderController {

	@Autowired
	private CustOrderService orderService;

	@GetMapping
	public ResponseEntity<List<CustOrder>> getOrders(Authentication auth) {
		String username = auth.getName();
		return new ResponseEntity<List<CustOrder>>(orderService.getOrders(username), HttpStatus.OK);

	}

	@PostMapping("/add")
	public ResponseEntity<String> createOrder(Authentication auth, @RequestBody CustOrderDTO order) {
		String username = auth.getName();
		try {
			return new ResponseEntity<String>(orderService.createOrder(order, username), HttpStatus.ACCEPTED);
		} 
		catch (BadRequestException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> cancelOrder(Authentication auth, @RequestBody orderDeleteDTO orderDTO) {
		try {
			String username = auth.getName();

			return new ResponseEntity<>(orderService.cancelOrder(username, orderDTO.getOrderIds()), HttpStatus.OK);
		} catch (OrderNotFoundExcpetion | BadRequestException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> cancelOrder(Authentication auth, @PathVariable(name = "id") Long id,
			@RequestBody orderDeleteDTO orderDTO) {
		try {
			String username = auth.getName();
			return new ResponseEntity<>(orderService.cancelOrder(username, id, orderDTO.getOrderIds()), HttpStatus.OK);
		} catch (OrderNotFoundExcpetion | BadRequestException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
