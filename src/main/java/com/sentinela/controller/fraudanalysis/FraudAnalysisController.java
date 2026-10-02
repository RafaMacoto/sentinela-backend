package com.sentinela.controller.fraudanalysis;

import com.sentinela.dto.fraudanalysis.CreateFraudAnalysisRequest;
import com.sentinela.dto.fraudanalysis.FraudAnalysisResponse;
import com.sentinela.service.fraudanalysis.IFraudAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fraud-analyses")
@RequiredArgsConstructor
public class FraudAnalysisController {

	private final IFraudAnalysisService fraudAnalysisService;

	@Operation(summary = "Create a fraud analysis", description = "Stores a risk analysis for an existing transaction.",
			responses = {
					@ApiResponse(responseCode = "201", description = "Fraud analysis created successfully"),
					@ApiResponse(responseCode = "400", description = "Request validation failed"),
					@ApiResponse(responseCode = "404", description = "Transaction was not found")
			})
	@PostMapping
	public ResponseEntity<FraudAnalysisResponse> create(
			@Valid @RequestBody CreateFraudAnalysisRequest request) {
		FraudAnalysisResponse created = fraudAnalysisService.create(request);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(created.id()).toUri();
		return ResponseEntity.created(location).body(created);
	}

	@Operation(summary = "List fraud analyses", description = "Returns fraud analyses ordered from newest to oldest.",
			responses = @ApiResponse(responseCode = "200", description = "Fraud analyses retrieved successfully"))
	@GetMapping
	public ResponseEntity<List<FraudAnalysisResponse>> getAll() {
		return ResponseEntity.ok(fraudAnalysisService.getAll());
	}

	@Operation(summary = "Get a fraud analysis", description = "Returns a fraud analysis by UUID.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Fraud analysis retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "Fraud analysis was not found")
			})
	@GetMapping("/{id}")
	public ResponseEntity<FraudAnalysisResponse> getById(@PathVariable UUID id) {
		return ResponseEntity.ok(fraudAnalysisService.getById(id));
	}

	@Operation(summary = "List analyses for a transaction",
			description = "Returns all fraud analyses associated with a transaction UUID.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Fraud analyses retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "Transaction was not found")
			})
	@GetMapping("/transaction/{transactionId}")
	public ResponseEntity<List<FraudAnalysisResponse>> getByTransactionId(@PathVariable UUID transactionId) {
		return ResponseEntity.ok(fraudAnalysisService.getByTransactionId(transactionId));
	}
}
