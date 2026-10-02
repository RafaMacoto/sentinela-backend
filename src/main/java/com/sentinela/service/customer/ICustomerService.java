package com.sentinela.service.customer;

import com.sentinela.dto.customer.CreateCustomerRequest;
import com.sentinela.dto.customer.CustomerResponse;
import com.sentinela.dto.customer.UpdateCustomerRequest;

import java.util.List;
import java.util.UUID;

public interface ICustomerService {

	CustomerResponse createCustomer(CreateCustomerRequest request);

	List<CustomerResponse> getCustomers();

	CustomerResponse getCustomerById(UUID id);

	CustomerResponse updateCustomer(UUID id, UpdateCustomerRequest request);

	void deleteCustomer(UUID id);
}
