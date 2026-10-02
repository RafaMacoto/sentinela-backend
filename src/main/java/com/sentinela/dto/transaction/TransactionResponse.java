package com.sentinela.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
		UUID id,
		UUID customerId,
		UUID accountId,
		UUID recipientId,
		UUID deviceId,
		BigDecimal amount,
		LocalDateTime timestamp,
		boolean newDevice,
		boolean newRecipient) {
}
