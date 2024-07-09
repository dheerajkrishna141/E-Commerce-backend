package com.ECommerce.ECommercebackend.Aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ECommerce.ECommercebackend.Entity.LocalUser;
import com.ECommerce.ECommercebackend.Repository.UserRepo;

@Aspect
@Component
public class UserCheckAspect {
	@Autowired
	private UserRepo userRepo;

	@Before("execution(* com.ECommerce.ECommercebackend.ServiceImpl.*.*(..)) && @within(com.ECommerce.ECommercebackend.Annotation.UserAvailabiltyCheck) && args(username,..) ")
	public void UserAvailabilityCheck(JoinPoint jp, String username) throws Throwable {
		LocalUser Luser = userRepo.findByEmail(username);
		if (Luser == null) {
			throw new UsernameNotFoundException("user with username: " + username + " not found!");
		}
	}

}
