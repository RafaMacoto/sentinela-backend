package com.sentinela.dto.customer;

import java.time.Instant;
import java.util.UUID;

public record CustomerResponse(
		UUID id,
		String name,
		String email,
		String documentNumber,
		String phoneNumber,
		Instant createdAt,
		Instant updatedAt) {
}
