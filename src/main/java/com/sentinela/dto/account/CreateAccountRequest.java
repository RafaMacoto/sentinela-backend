package com.sentinela.dto.account;

import com.sentinela.entity.account.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateAccountRequest(
		@NotNull UUID customerId,
		@NotBlank @Size(max = 34) String accountNumber,
		@NotNull AccountType accountType,
		@NotBlank @Pattern(regexp = "[A-Za-z]{3}") String currency) {
}
