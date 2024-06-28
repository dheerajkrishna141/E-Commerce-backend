package com.ECommerce.ECommercebackend.ServiceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ECommerce.ECommercebackend.Service.EncryptionService;

import jakarta.annotation.PostConstruct;

@Service
public class EncryptionImpl implements EncryptionService{
	@Value("${encryption.salt.rounds}")
	private int saltRounds;
	
	private String salt;
	
	@PostConstruct
	public void PostConstruct() {
		salt = BCrypt.gensalt();
	}
	@Override
	public String EncryptPassword(String password) {
		
		return BCrypt.hashpw(password, salt);
	}

	@Override
	public boolean VerifyPassword(String password, String hash) {
		
		return BCrypt.checkpw(password, hash);
	}

}
