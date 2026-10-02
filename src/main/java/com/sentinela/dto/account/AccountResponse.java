package com.sentinela.dto.account;

import com.sentinela.entity.account.AccountType;

import java.time.Instant;
import java.util.UUID;

public record AccountResponse(
		UUID id,
		UUID customerId,
		String accountNumber,
		AccountType accountType,
		String currency,
		Instant createdAt,
		Instant updatedAt) {
}
