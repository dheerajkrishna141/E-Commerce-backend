package com.ECommerce.ECommercebackend.Payload;

import com.ECommerce.ECommercebackend.Entity.LocalUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

	private LocalUser user;
	private boolean success;
	private String message;
}
