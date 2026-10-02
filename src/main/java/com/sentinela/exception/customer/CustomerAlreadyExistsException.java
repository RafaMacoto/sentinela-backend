package com.sentinela.exception.customer;

public class CustomerAlreadyExistsException extends RuntimeException {

	public CustomerAlreadyExistsException(String field) {
		super("A customer with this " + field + " already exists.");
	}
}
