package com.ECommerce.ECommercebackend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.ECommerce.ECommercebackend.Entity.LocalUser;
import com.ECommerce.ECommercebackend.Entity.VerificationToken;
import com.ECommerce.ECommercebackend.Exceptions.EmailFailureException;
import com.ECommerce.ECommercebackend.Service.EmailService;

@Service
public class EmailImpl implements EmailService {

	@Value("${email.from}")
	private String fromAddress;

	@Value("${app.frontend.url}")
	private String url;

	@Autowired
	private JavaMailSenderImpl javaMailSender;

	private SimpleMailMessage makeMailMessage(VerificationToken verificationToken) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromAddress);
		message.setTo(verificationToken.getUser().getEmail());
		message.setSubject("Verify your email to activate your account.");
		message.setText("Please follow the link below to verify your email to activate your account.\n" + url
				+ "/user/verify?token=" + verificationToken.getToken());
		
		return message;

	}

	@Override
	public void sendVerificationEmail(VerificationToken verificationToken) {
		// TODO Auto-generated method stub
		SimpleMailMessage message = makeMailMessage(verificationToken);

		try {
			javaMailSender.send(message);

		} catch (MailException e) {
			throw new EmailFailureException("Email could not be sent!");
		}

	}
	private SimpleMailMessage createPRMessage(LocalUser user, String token) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromAddress);
		message.setTo(user.getEmail());
		message.setSubject("Reset your password.");
		message.setText("follow the below link to reset you account password.\n"+
		url+"/verfiy?="+token);
		
		return message;
		
		
	}

	@Override
	public void sendRPVeriificationEmail(LocalUser user , String token) {
		// TODO Auto-generated method stub
		SimpleMailMessage message = createPRMessage(user, token);
		try {
			javaMailSender.send(message);
		} catch (MailException e) {
			// TODO Auto-generated catch block
			throw new  EmailFailureException("Email could not be sent.");
		}
		
	}

}
