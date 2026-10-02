package com.sentinela.service.device;

import com.sentinela.dto.device.CreateDeviceRequest;
import com.sentinela.dto.device.DeviceResponse;
import com.sentinela.dto.device.UpdateDeviceRequest;

import java.util.List;
import java.util.UUID;

public interface IDeviceService {

	DeviceResponse create(CreateDeviceRequest request);

	List<DeviceResponse> getAll();

	DeviceResponse getById(UUID id);

	DeviceResponse update(UUID id, UpdateDeviceRequest request);

	void delete(UUID id);
}
