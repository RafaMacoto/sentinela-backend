package com.sentinela.controller.transaction;

import com.sentinela.dto.transaction.TransactionRequest;
import com.sentinela.dto.transaction.TransactionResponse;
import com.sentinela.service.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionService transactionService;

	@Operation(
			summary = "Create a transaction",
			description = "Persists a transaction linked to an existing customer and registered device.",
			responses = {
					@ApiResponse(responseCode = "201", description = "Transaction created successfully"),
					@ApiResponse(responseCode = "400", description = "Request is invalid or related resources do not match"),
					@ApiResponse(responseCode = "404", description = "Customer, account, or device was not found")
			})
	@PostMapping
	public ResponseEntity<TransactionResponse> create(@Valid @RequestBody TransactionRequest request) {
		TransactionResponse transaction = transactionService.create(request);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(transaction.id())
				.toUri();

		return ResponseEntity.created(location).body(transaction);
	}
}
