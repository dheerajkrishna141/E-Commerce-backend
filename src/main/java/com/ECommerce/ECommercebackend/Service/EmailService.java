package com.ECommerce.ECommercebackend.Service;

import com.ECommerce.ECommercebackend.Entity.LocalUser;
import com.ECommerce.ECommercebackend.Entity.VerificationToken;

public interface EmailService {
	
	public void sendVerificationEmail(VerificationToken verificationToken);
	
	public void sendRPVeriificationEmail(LocalUser user, String token);

}
