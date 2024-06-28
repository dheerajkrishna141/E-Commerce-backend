//package com.ECommerce.ECommercebackend.Filters;
//
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.ECommerce.ECommercebackend.Entity.LocalUser;
//import com.ECommerce.ECommercebackend.Entity.VerificationToken;
//import com.ECommerce.ECommercebackend.Exceptions.UserNotVerifiedException;
//import com.ECommerce.ECommercebackend.Repository.UserRepo;
//import com.ECommerce.ECommercebackend.Repository.VerificationRepo;
//import com.ECommerce.ECommercebackend.Service.EmailService;
//import com.ECommerce.ECommercebackend.Service.JWTService;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.transaction.Transactional;
//
////@Component
//public class EmailVerificationFilter extends OncePerRequestFilter {
//	
//	@Autowired
//	private UserRepo userRepo;
//
//	@Autowired
//	private JWTService jwt;
//	
//	@Autowired
//	private VerificationRepo verificationRepo;
//	
//	@Autowired
//	private EmailService emailService;
//	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		LocalUser Luser = userRepo.findByEmail(auth.getName());
//		if(!Luser.isEmailVerified()) {
//			List<VerificationToken> verificationTokens = Luser.getVerificationTokens();
//			if(verificationTokens.size()==0) {
//				VerificationToken verificationToken = createVerificationToken(Luser);
//				verificationRepo.save(verificationToken);
//				emailService.sendVerificationEmail(verificationToken);
//				throw new UserNotVerifiedException("New verification Email sent, please verify.");
//			}
//			else {
//				throw new UserNotVerifiedException("please verify your account before logging in.");
//				
//			}
//			
//		}
//		filterChain.doFilter(request, response);
//		
//		
//	}
//	private VerificationToken createVerificationToken(LocalUser user) {
//		VerificationToken verificationToken = new VerificationToken();
//		verificationToken.setToken(jwt.generateVerificationJWT(user));
//		verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
//		verificationToken.setUser(user);
////		user.getVerificationTokens().add(verificationToken);
//
//		return verificationToken;
//	}
//
//}
