package com.sentinela.service.transaction;

import com.sentinela.dto.transaction.TransactionRequest;
import com.sentinela.dto.transaction.TransactionResponse;

public interface TransactionService {

	TransactionResponse create(TransactionRequest request);
}
