package com.ECommerce.ECommercebackend.Service;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.ECommerce.ECommercebackend.Entity.Address;
import com.ECommerce.ECommercebackend.Entity.LocalUser;
import com.ECommerce.ECommercebackend.Payload.AddressDTO;
import com.ECommerce.ECommercebackend.Payload.LoginResponseDTO;
import com.ECommerce.ECommercebackend.Payload.PasswordResetDTO;
import com.ECommerce.ECommercebackend.Payload.UserDTO;

public interface UserService {

	public LocalUser createUser(UserDTO user) throws BadRequestException;

	public String deleteUser(long userId);


	public Address addAddress(AddressDTO address, String username) throws Exception;

	public boolean verifyUser(String token);

	public String sendRPLink(String email);
	
	public LoginResponseDTO login(String username);

	public boolean resetPassword(PasswordResetDTO prDTO);
	
	public List<Address> getAddresses(String username);


}
