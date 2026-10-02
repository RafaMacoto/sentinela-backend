package com.sentinela.controller.device;

import com.sentinela.dto.device.CreateDeviceRequest;
import com.sentinela.dto.device.DeviceResponse;
import com.sentinela.dto.device.UpdateDeviceRequest;
import com.sentinela.service.device.IDeviceService;
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
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

	private final IDeviceService deviceService;

	@Operation(summary = "Register a device", description = "Registers a device for an existing customer.",
			responses = {
					@ApiResponse(responseCode = "201", description = "Device registered successfully"),
					@ApiResponse(responseCode = "400", description = "Request validation failed"),
					@ApiResponse(responseCode = "404", description = "Customer was not found"),
					@ApiResponse(responseCode = "409", description = "Device identifier is already registered")
			})
	@PostMapping
	public ResponseEntity<DeviceResponse> create(@Valid @RequestBody CreateDeviceRequest request) {
		DeviceResponse created = deviceService.create(request);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(created.id()).toUri();
		return ResponseEntity.created(location).body(created);
	}

	@Operation(summary = "List devices", description = "Returns all registered devices.",
			responses = @ApiResponse(responseCode = "200", description = "Devices retrieved successfully"))
	@GetMapping
	public ResponseEntity<List<DeviceResponse>> getAll() {
		return ResponseEntity.ok(deviceService.getAll());
	}

	@Operation(summary = "Get a device", description = "Returns a device by UUID.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Device retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "Device was not found")
			})
	@GetMapping("/{id}")
	public ResponseEntity<DeviceResponse> getById(@PathVariable UUID id) {
		return ResponseEntity.ok(deviceService.getById(id));
	}

	@Operation(summary = "Update a device", description = "Updates the device registration and customer owner.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Device updated successfully"),
					@ApiResponse(responseCode = "400", description = "Request validation failed"),
					@ApiResponse(responseCode = "404", description = "Device or customer was not found"),
					@ApiResponse(responseCode = "409", description = "Device identifier is already registered")
			})
	@PutMapping("/{id}")
	public ResponseEntity<DeviceResponse> update(
			@PathVariable UUID id,
			@Valid @RequestBody UpdateDeviceRequest request) {
		return ResponseEntity.ok(deviceService.update(id, request));
	}

	@Operation(summary = "Delete a device", description = "Deletes a device by UUID.",
			responses = {
					@ApiResponse(responseCode = "204", description = "Device deleted successfully"),
					@ApiResponse(responseCode = "404", description = "Device was not found"),
					@ApiResponse(responseCode = "409", description = "Device is referenced by a transaction")
			})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		deviceService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
