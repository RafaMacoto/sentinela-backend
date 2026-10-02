package com.sentinela.dto.device;

import java.time.Instant;
import java.util.UUID;

public record DeviceResponse(
		UUID id,
		UUID customerId,
		String deviceIdentifier,
		String deviceType,
		boolean trusted,
		Instant createdAt,
		Instant updatedAt) {
}
