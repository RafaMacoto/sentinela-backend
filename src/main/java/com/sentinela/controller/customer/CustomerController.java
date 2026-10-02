package com.sentinela.controller.customer;

import com.sentinela.dto.customer.CreateCustomerRequest;
import com.sentinela.dto.customer.CustomerResponse;
import com.sentinela.dto.customer.UpdateCustomerRequest;
import com.sentinela.service.customer.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

	private final ICustomerService customerService;

	@Operation(
			summary = "Create a customer",
			description = "Creates a customer profile for future account, device, and transaction tracking.",
			responses = {
					@ApiResponse(responseCode = "201", description = "Customer created successfully"),
					@ApiResponse(responseCode = "400", description = "Request validation failed"),
					@ApiResponse(responseCode = "409", description = "Email or document number is already registered")
			})
	@PostMapping
	public ResponseEntity<CustomerResponse> createCustomer(
			@Valid @RequestBody CreateCustomerRequest request) {
		CustomerResponse created = customerService.createCustomer(request);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(created.id())
				.toUri();

		return ResponseEntity.created(location).body(created);
	}

	@Operation(
			summary = "List customers",
			description = "Returns all customers ordered by name.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Customers retrieved successfully")
			})
	@GetMapping
	public ResponseEntity<List<CustomerResponse>> getCustomers() {
		return ResponseEntity.ok(customerService.getCustomers());
	}

	@Operation(
			summary = "Get a customer",
			description = "Returns a customer by its UUID.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Customer retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "Customer was not found")
			})
	@GetMapping("/{id}")
	public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable UUID id) {
		return ResponseEntity.ok(customerService.getCustomerById(id));
	}

	@Operation(
			summary = "Update a customer",
			description = "Replaces the customer's editable profile fields.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Customer updated successfully"),
					@ApiResponse(responseCode = "400", description = "Request validation failed"),
					@ApiResponse(responseCode = "404", description = "Customer was not found"),
					@ApiResponse(responseCode = "409", description = "Email or document number is already registered")
			})
	@PutMapping("/{id}")
	public ResponseEntity<CustomerResponse> updateCustomer(
			@PathVariable UUID id,
			@Valid @RequestBody UpdateCustomerRequest request) {
		return ResponseEntity.ok(customerService.updateCustomer(id, request));
	}

	@Operation(
			summary = "Delete a customer",
			description = "Deletes a customer by its UUID.",
			responses = {
					@ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
					@ApiResponse(responseCode = "404", description = "Customer was not found"),
					@ApiResponse(responseCode = "409", description = "Customer is referenced by existing data")
			})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
		customerService.deleteCustomer(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
