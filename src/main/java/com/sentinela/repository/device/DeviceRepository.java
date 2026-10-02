package com.sentinela.repository.device;

import com.sentinela.entity.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

	boolean existsByCustomerIdAndDeviceIdentifierIgnoreCase(UUID customerId, String deviceIdentifier);

	boolean existsByIdAndCustomerId(UUID id, UUID customerId);

	boolean existsByCustomerIdAndDeviceIdentifierIgnoreCaseAndIdNot(
			UUID customerId, String deviceIdentifier, UUID id);

	List<Device> findAllByOrderByCreatedAtDesc();
}
