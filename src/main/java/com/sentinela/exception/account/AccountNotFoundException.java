package com.sentinela.exception.account;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {

	public AccountNotFoundException(UUID id) {
		super("Account not found with id: " + id);
	}
}
