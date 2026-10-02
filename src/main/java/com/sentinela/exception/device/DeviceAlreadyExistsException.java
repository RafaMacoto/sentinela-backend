package com.sentinela.exception.device;

import java.util.UUID;

public class DeviceAlreadyExistsException extends RuntimeException {

	public DeviceAlreadyExistsException(UUID customerId, String identifier) {
		super("Device identifier '" + identifier + "' is already registered for customer " + customerId + ".");
	}
}
