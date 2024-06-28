package com.ECommerce.ECommercebackend.Configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class Config {

	@Bean
	public ModelMapper modelMapper(){
		
		return new ModelMapper();
	}
	
	@Bean
	public JavaMailSenderImpl mailsender() {
		return new JavaMailSenderImpl();
	}
}
