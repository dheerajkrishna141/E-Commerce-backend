package com.ECommerce.ECommercebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ECommerce.ECommercebackend.Entity.Address;
import com.ECommerce.ECommercebackend.Entity.LocalUser;
import com.ECommerce.ECommercebackend.Exceptions.EmailFailureException;
import com.ECommerce.ECommercebackend.Payload.AddressDTO;
import com.ECommerce.ECommercebackend.Payload.PasswordResetDTO;
import com.ECommerce.ECommercebackend.Payload.UserDTO;
import com.ECommerce.ECommercebackend.Service.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	

	

	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO user) {
		try {
			LocalUser Luser = userService.createUser(user);
			return new ResponseEntity<LocalUser>(Luser, HttpStatus.CREATED);
		} catch (EmailFailureException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}

	}


	@PostMapping("/verify")
	public ResponseEntity<?> verifyEmail(@RequestParam(name = "token") String token) {
		if (userService.verifyUser(token)) {

			return new ResponseEntity<>("User successfully verfied", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable(name = "id") long id) {
		return new ResponseEntity<String>(userService.deleteUser(id), HttpStatus.OK);
	}

	@GetMapping("/me")
	public ResponseEntity<?> getLoggedInUser(Authentication auth) {

		try {
			
			return new ResponseEntity<>(userService.login(auth.getName()), HttpStatus.OK);
		} catch (EmailFailureException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/resetPassword")
	public ResponseEntity<?> sendPasswordResetLink(@RequestBody String email) {
		try {
			return new ResponseEntity<>(userService.sendRPLink(email), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<?> PasswordReset(@Valid @RequestBody  PasswordResetDTO pRDTO) {
		if (userService.resetPassword(pRDTO)) {
			return new ResponseEntity<>("Password resetted successfully!", HttpStatus.ACCEPTED);
		} else {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}


	 	
	@PostMapping("/address")
	public ResponseEntity<?> addAddress(Authentication user,
			@RequestBody AddressDTO address) {
		try {
			String username = user.getName();
			return new ResponseEntity<Address>(userService.addAddress(address, username), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/address")
	public ResponseEntity<?> getAddresses(Authentication user){
		String username = user.getName();
		
		try {
			return new ResponseEntity<>(userService.getAddresses(username), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
