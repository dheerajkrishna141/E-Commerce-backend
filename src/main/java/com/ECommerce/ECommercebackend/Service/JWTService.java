package com.ECommerce.ECommercebackend.Service;

import com.ECommerce.ECommercebackend.Entity.LocalUser;
import com.ECommerce.ECommercebackend.Payload.LoginDTO;

public interface JWTService {
	
	public String GenerateJWT(LoginDTO user);
	
	public String GetUsername(String token);
	public String generateVerificationJWT(LocalUser user);
	public String generatePasswordResetJWT(LocalUser user);
	public String getEmail(String token);

}
