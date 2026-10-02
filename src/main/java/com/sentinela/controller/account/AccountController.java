package com.sentinela.controller.account;

import com.sentinela.dto.account.AccountResponse;
import com.sentinela.dto.account.CreateAccountRequest;
import com.sentinela.dto.account.UpdateAccountRequest;
import com.sentinela.service.account.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

	private final IAccountService accountService;

	@Operation(summary = "Create an account", description = "Creates an account owned by an existing customer.",
			responses = {
					@ApiResponse(responseCode = "201", description = "Account created successfully"),
					@ApiResponse(responseCode = "400", description = "Request validation failed"),
					@ApiResponse(responseCode = "404", description = "Customer was not found"),
					@ApiResponse(responseCode = "409", description = "Account number is already registered")
			})
	@PostMapping
	public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
		AccountResponse created = accountService.create(request);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(created.id()).toUri();
		return ResponseEntity.created(location).body(created);
	}

	@Operation(summary = "List accounts", description = "Returns all registered accounts.",
			responses = @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully"))
	@GetMapping
	public ResponseEntity<List<AccountResponse>> getAll() {
		return ResponseEntity.ok(accountService.getAll());
	}

	@Operation(summary = "Get an account", description = "Returns an account by UUID.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Account retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "Account was not found")
			})
	@GetMapping("/{id}")
	public ResponseEntity<AccountResponse> getById(@PathVariable UUID id) {
		return ResponseEntity.ok(accountService.getById(id));
	}

	@Operation(summary = "Update an account", description = "Updates an account and its customer ownership.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Account updated successfully"),
					@ApiResponse(responseCode = "400", description = "Request validation failed"),
					@ApiResponse(responseCode = "404", description = "Account or customer was not found"),
					@ApiResponse(responseCode = "409", description = "Account number is already registered")
			})
	@PutMapping("/{id}")
	public ResponseEntity<AccountResponse> update(
			@PathVariable UUID id,
			@Valid @RequestBody UpdateAccountRequest request) {
		return ResponseEntity.ok(accountService.update(id, request));
	}

	@Operation(summary = "Delete an account", description = "Deletes an account by UUID.",
			responses = {
					@ApiResponse(responseCode = "204", description = "Account deleted successfully"),
					@ApiResponse(responseCode = "404", description = "Account was not found"),
					@ApiResponse(responseCode = "409", description = "Account is referenced by a transaction")
			})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		accountService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
