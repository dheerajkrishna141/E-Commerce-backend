package com.ECommerce.ECommercebackend.Payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDTO {

	@NotNull
	@NotBlank
	private String token;

	@NotBlank
	@NotNull
	private String newPassword;

}
