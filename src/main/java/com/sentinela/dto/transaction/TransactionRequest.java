package com.sentinela.dto.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionRequest(
		@NotNull UUID customerId,
		UUID accountId,
		@NotNull UUID recipientId,
		@NotNull UUID deviceId,
		@NotNull @Positive BigDecimal amount,
		@NotNull LocalDateTime timestamp,
		boolean newDevice,
		boolean newRecipient) {
}
