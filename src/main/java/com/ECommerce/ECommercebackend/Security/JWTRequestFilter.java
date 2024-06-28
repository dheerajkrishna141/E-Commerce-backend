package com.ECommerce.ECommercebackend.Security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ECommerce.ECommercebackend.Entity.LocalUser;
import com.ECommerce.ECommercebackend.Repository.UserRepo;
import com.ECommerce.ECommercebackend.Service.JWTService;
import com.auth0.jwt.exceptions.JWTDecodeException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTRequestFilter  extends OncePerRequestFilter{
	
	@Autowired
	private JWTService JwtService;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String tokenHeader = request.getHeader("Authorization");
		if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
			String token  = tokenHeader.substring(7);
			try {
				
				String username = JwtService.GetUsername(token);
				LocalUser Luser= userRepo.findByEmail(username);
				if(Luser!= null) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(Luser, null, new ArrayList<>());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
			catch(JWTDecodeException e) {
				throw new JWTDecodeException(e.getMessage());
				
			}
		}
		filterChain.doFilter(request, response);
		// TODO Auto-generated method stub
		
	}

}
