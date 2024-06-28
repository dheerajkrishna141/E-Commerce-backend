package com.ECommerce.ECommercebackend.Payload;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	@NotBlank(message = "email is mandatory")
	@Email
	private String email;
	@NotBlank(message = "password is mandatory")
	@NotNull
	private String password;
	@NotNull
	@NotBlank(message = "firstname is mandatory")
	private String firstName;
	@NotNull
	@NotBlank
	private String lastName;
	@NotNull
	private Set<String> roles;
}
