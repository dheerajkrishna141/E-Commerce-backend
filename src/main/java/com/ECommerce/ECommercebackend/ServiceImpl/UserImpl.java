package com.ECommerce.ECommercebackend.ServiceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ECommerce.ECommercebackend.Entity.Address;
import com.ECommerce.ECommercebackend.Entity.LocalUser;
import com.ECommerce.ECommercebackend.Entity.Roles;
import com.ECommerce.ECommercebackend.Entity.VerificationToken;
import com.ECommerce.ECommercebackend.Exceptions.UserNotFoundException;
import com.ECommerce.ECommercebackend.Exceptions.UserNotUniqueException;
import com.ECommerce.ECommercebackend.Exceptions.UserNotVerifiedException;
import com.ECommerce.ECommercebackend.Payload.AddressDTO;
import com.ECommerce.ECommercebackend.Payload.LoginResponseDTO;
import com.ECommerce.ECommercebackend.Payload.PasswordResetDTO;
import com.ECommerce.ECommercebackend.Payload.UserDTO;
import com.ECommerce.ECommercebackend.Repository.AddressRepo;
import com.ECommerce.ECommercebackend.Repository.RolesRepository;
import com.ECommerce.ECommercebackend.Repository.UserRepo;
import com.ECommerce.ECommercebackend.Repository.VerificationRepo;
import com.ECommerce.ECommercebackend.Service.EmailService;
import com.ECommerce.ECommercebackend.Service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EncryptionImpl encrpyt;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private RolesRepository roleRepo;

	@Autowired
	private JWTImpl jwt;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ModelMapper modelmapper;

	@Autowired
	private VerificationRepo verificationRepo;

	@Override
	@Transactional
	public LocalUser createUser(UserDTO user) throws BadRequestException {
		// TODO Auto-generated method stub
		LocalUser Luser = userRepo.findByEmail(user.getEmail());
		if (Luser != null) {
			throw new UserNotUniqueException("Email: " + user.getEmail() + " is already registered");
		}

		else {
			Luser = new LocalUser();
			Luser.setEmail(user.getEmail());
			Luser.setFirstName(user.getFirstName());
			Luser.setLastName(user.getLastName());
			Luser.setPassword(encrpyt.EncryptPassword(user.getPassword()));

			for (String r : user.getRoles()) {

				Roles role = roleRepo.findByName(r);
				Luser.getRoles().add(role);
			}

			Luser = userRepo.save(Luser);
			VerificationToken verificationToken = createVerificationToken(Luser);
			verificationRepo.save(verificationToken);
			emailService.sendVerificationEmail(verificationToken);

			return Luser;
		}
	}

	private VerificationToken createVerificationToken(LocalUser user) {
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(jwt.generateVerificationJWT(user));
		verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
		verificationToken.setUser(user);
		return verificationToken;
	}

	@Override
	public String deleteUser(long userId) {

		LocalUser Luser = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
		userRepo.deleteById(Luser.getId());
		return "Successfully deleted";
	}

	@Override
	public Address addAddress(AddressDTO address, String username) throws Exception {

		LocalUser Luser = userRepo.findByEmail(username);
		if (Luser == null) {
			throw new UserNotFoundException("user not found!");
		}
		if(!Luser.isEmailVerified()) {
			throw new UserNotVerifiedException("Please verify your account!");
		}
		try {
		Address newAddress = modelmapper.map(address, Address.class);
		newAddress.setUser(Luser);
			return addressRepo.save(newAddress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		}
	}

	@Transactional
	@Override
	public boolean verifyUser(String token) {
		VerificationToken verificationToken = verificationRepo.findByToken(token);
		if (verificationToken != null) {
			LocalUser Luser = verificationToken.getUser();
			if (!Luser.isEmailVerified()) {
				Luser.setEmailVerified(true);
				userRepo.save(Luser);
				verificationRepo.deleteByUser(Luser);
				return true;
			}

		}
		return false;
	}

	public String sendRPLink(String email) {

		LocalUser Luser = userRepo.findByEmail(email);
		if (Luser != null) {

			String pRToken = jwt.generatePasswordResetJWT(Luser);
			emailService.sendRPVeriificationEmail(Luser, pRToken);
			return "Email sent successfully!";

		}
		throw new UserNotFoundException("User not found!");
	}

	@Override
	@Transactional
	public boolean resetPassword(PasswordResetDTO pRDTO) {
		LocalUser Luser = userRepo.findByEmail(jwt.getEmail(pRDTO.getToken()));
		if (Luser != null) {
			String encryptedPass = encrpyt.EncryptPassword(pRDTO.getNewPassword());
			Luser.setPassword(encryptedPass);
			userRepo.save(Luser);
			return true;

		}
		System.out.println(Luser);

		return false;
	}

	@Override
	@Transactional
	public LoginResponseDTO login(String username) {
		LocalUser Luser = userRepo.findByEmail(username);
		if(Luser == null) {
			throw new UserNotFoundException("User not Found!");
		}
		if (!Luser.isEmailVerified()) {
			List<VerificationToken> verificationTokens = Luser.getVerificationTokens();

			if (verificationTokens.size() == 0 || verificationTokens.get(0).getCreatedTimestamp()
					.before(new Date(System.currentTimeMillis() - 5 * 60 * 1000))) {
				VerificationToken verificationToken = createVerificationToken(Luser);
				verificationRepo.save(verificationToken);
				emailService.sendVerificationEmail(verificationToken);
				return new LoginResponseDTO(null, false, "New verification email sent, please verify your account!");

			} else {
				return new LoginResponseDTO(null, false, "please verify your account before logging in.");

			}

		}
		return new LoginResponseDTO(Luser, true, "Successfully logged in");
	}
	


	@Override
	public List<Address> getAddresses(String username) {
		// TODO Auto-generated method stub
		LocalUser Luser = userRepo.findByEmail(username);
		if(Luser != null) {
			return Luser.getAddresses();
		}
		throw new UserNotFoundException("user not found!");
	}

}
