package com.sentinela.exception.account;

public class AccountAlreadyExistsException extends RuntimeException {

	public AccountAlreadyExistsException() {
		super("An account with this account number already exists.");
	}
}
