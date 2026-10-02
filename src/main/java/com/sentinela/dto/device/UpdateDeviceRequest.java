package com.sentinela.dto.device;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateDeviceRequest(
		@NotNull UUID customerId,
		@NotBlank @Size(max = 160) String deviceIdentifier,
		@NotBlank @Size(max = 40) String deviceType,
		boolean trusted) {
}
