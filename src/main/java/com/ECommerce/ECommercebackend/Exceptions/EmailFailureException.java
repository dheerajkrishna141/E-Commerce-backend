package com.ECommerce.ECommercebackend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailFailureException extends RuntimeException{
	
	private String mes;

	public EmailFailureException(String mes) {
		super(mes);
		this.mes = mes;
	}
	

}
