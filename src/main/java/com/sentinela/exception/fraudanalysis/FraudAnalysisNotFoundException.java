package com.sentinela.exception.fraudanalysis;

import java.util.UUID;

public class FraudAnalysisNotFoundException extends RuntimeException {

	public FraudAnalysisNotFoundException(UUID id) {
		super("Fraud analysis not found with id: " + id);
	}
}
