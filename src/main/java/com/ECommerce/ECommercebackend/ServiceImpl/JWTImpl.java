package com.ECommerce.ECommercebackend.ServiceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ECommerce.ECommercebackend.Entity.LocalUser;
import com.ECommerce.ECommercebackend.Payload.LoginDTO;
import com.ECommerce.ECommercebackend.Service.JWTService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;

@Service
public class JWTImpl implements JWTService{

	@Value("${jwt.algorithm.key}")
	private String AlgorithmKey;
	@Value("${jwt.issuer}")
	private String Issuer;
	@Value("${jwt.ExpiryInSeconds}")
	private int ExpiryInSeconds;
	
	private Algorithm algorithm;
	
	private static final String USERNAME_KEY="USERNAME";
	private static final String VERIFICATION_EMAIL_KEY="VERIFICATION_EMAIL";
	private static final String RESET_PASSWORD_EMAIL_KEY = "RESET_PASSWORD_EMAIL";
	
	@PostConstruct
	public void AlgoKey() {
		algorithm = Algorithm.HMAC256(AlgorithmKey);
	}
	
	@Override
	public String GenerateJWT(LoginDTO user) {
		
		
		return JWT.create().withClaim(USERNAME_KEY, user.getUserName())
				.withExpiresAt(new Date(System.currentTimeMillis()+ (1000* ExpiryInSeconds)))
				.withIssuer(Issuer)
				.sign(algorithm);
	}
	
	public String generateVerificationJWT(LocalUser user) {
		return JWT.create().withClaim(VERIFICATION_EMAIL_KEY, user.getEmail())
				.withExpiresAt(new Date(System.currentTimeMillis()+ (1000* ExpiryInSeconds)))
				.withIssuer(Issuer)
				.sign(algorithm);
		
	}
	public String generatePasswordResetJWT(LocalUser user) {
		return JWT.create().withClaim(RESET_PASSWORD_EMAIL_KEY, user.getEmail())
				.withExpiresAt(new Date(System.currentTimeMillis()+ (1000 * 60 * 5)))
				.withIssuer(Issuer)
				.sign(algorithm);
		
	}

	@Override
	public String GetUsername(String token) {
		
		DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
		
		return jwt.getClaim(USERNAME_KEY).asString();
	}

	@Override
	public String getEmail(String token) {
		
		DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
		return jwt.getClaim(RESET_PASSWORD_EMAIL_KEY).asString();
	}
	
	

}
