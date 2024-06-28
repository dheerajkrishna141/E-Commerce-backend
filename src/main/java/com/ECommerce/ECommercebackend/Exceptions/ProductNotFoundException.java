package com.ECommerce.ECommercebackend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductNotFoundException extends RuntimeException{
	private String mes;

	public ProductNotFoundException(String mes) {
		super(mes);
		this.mes = mes;
	}
	
	
}
