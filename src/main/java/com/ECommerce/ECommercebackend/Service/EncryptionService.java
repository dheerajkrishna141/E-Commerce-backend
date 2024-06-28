package com.ECommerce.ECommercebackend.Service;

public interface EncryptionService {
	
	
	public String EncryptPassword(String password);
	
	public boolean VerifyPassword(String password, String hash);
	

}
