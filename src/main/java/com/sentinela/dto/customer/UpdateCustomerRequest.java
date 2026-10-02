package com.sentinela.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCustomerRequest(
		@NotBlank @Size(max = 120) String name,
		@NotBlank @Email @Size(max = 254) String email,
		@NotBlank @Size(max = 32) String documentNumber,
		@Size(max = 32) String phoneNumber) {
}
