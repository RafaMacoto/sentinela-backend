package com.sentinela.service.device;

import com.sentinela.dto.device.CreateDeviceRequest;
import com.sentinela.dto.device.DeviceResponse;
import com.sentinela.dto.device.UpdateDeviceRequest;
import com.sentinela.entity.customer.Customer;
import com.sentinela.entity.device.Device;
import com.sentinela.exception.customer.CustomerNotFoundException;
import com.sentinela.exception.device.DeviceAlreadyExistsException;
import com.sentinela.exception.device.DeviceNotFoundException;
import com.sentinela.repository.customer.CustomerRepository;
import com.sentinela.repository.device.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceServiceImpl implements IDeviceService {

	private final DeviceRepository deviceRepository;
	private final CustomerRepository customerRepository;

	@Override
	@Transactional
	public DeviceResponse create(CreateDeviceRequest request) {
		Customer customer = findCustomer(request.customerId());
		String identifier = request.deviceIdentifier().strip();
		validateUniqueIdentifier(customer.getId(), identifier, null);
		Device device = new Device(
				customer,
				identifier,
				request.deviceType().strip(),
				request.trusted());
		return toResponse(deviceRepository.save(device));
	}

	@Override
	public List<DeviceResponse> getAll() {
		return deviceRepository.findAllByOrderByCreatedAtDesc()
				.stream().map(DeviceServiceImpl::toResponse).toList();
	}

	@Override
	public DeviceResponse getById(UUID id) {
		return toResponse(findDevice(id));
	}

	@Override
	@Transactional
	public DeviceResponse update(UUID id, UpdateDeviceRequest request) {
		Device device = findDevice(id);
		Customer customer = findCustomer(request.customerId());
		String identifier = request.deviceIdentifier().strip();
		validateUniqueIdentifier(customer.getId(), identifier, id);
		device.update(customer, identifier, request.deviceType().strip(), request.trusted());
		return toResponse(deviceRepository.save(device));
	}

	@Override
	@Transactional
	public void delete(UUID id) {
		deviceRepository.delete(findDevice(id));
	}

	private void validateUniqueIdentifier(UUID customerId, String identifier, UUID excludedId) {
		boolean exists = excludedId == null
				? deviceRepository.existsByCustomerIdAndDeviceIdentifierIgnoreCase(customerId, identifier)
				: deviceRepository.existsByCustomerIdAndDeviceIdentifierIgnoreCaseAndIdNot(
						customerId, identifier, excludedId);
		if (exists) {
			throw new DeviceAlreadyExistsException(customerId, identifier);
		}
	}

	private Device findDevice(UUID id) {
		return deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));
	}

	private Customer findCustomer(UUID id) {
		return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
	}

	private static DeviceResponse toResponse(Device device) {
		return new DeviceResponse(
				device.getId(),
				device.getCustomer().getId(),
				device.getDeviceIdentifier(),
				device.getDeviceType(),
				device.isTrusted(),
				device.getCreatedAt(),
				device.getUpdatedAt());
	}
}
