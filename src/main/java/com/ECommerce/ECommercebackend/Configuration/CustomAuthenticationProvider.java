package com.ECommerce.ECommercebackend.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ECommerce.ECommercebackend.Entity.LocalUser;
import com.ECommerce.ECommercebackend.Repository.UserRepo;

import jakarta.transaction.Transactional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Override
	@Transactional
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();

		String pwd = authentication.getCredentials().toString();
		LocalUser Luser = userRepo.findByEmail(username);

		if (Luser != null) {
			if (passwordEncoder.matches(pwd, Luser.getPassword())) {

				return new UsernamePasswordAuthenticationToken(username, pwd, Luser.getRoles());

			} else {
				throw new BadCredentialsException("Invalid password.");
			}
		} else {
			throw new UsernameNotFoundException("username: " + authentication.getName() + " not found");
		}

	}

//	private VerificationToken createVerificationToken(LocalUser user) {
//		VerificationToken verificationToken = new VerificationToken();
//		verificationToken.setToken(jwt.generateVerificationJWT(user));
//		verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
//		verificationToken.setUser(user);
////		user.getVerificationTokens().add(verificationToken);
//
//		return verificationToken;
//	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));

	}
	
	

}
