package com.sentinela.service.customer;

import com.sentinela.dto.customer.CreateCustomerRequest;
import com.sentinela.dto.customer.CustomerResponse;
import com.sentinela.dto.customer.UpdateCustomerRequest;
import com.sentinela.entity.customer.Customer;
import com.sentinela.exception.customer.CustomerAlreadyExistsException;
import com.sentinela.exception.customer.CustomerNotFoundException;
import com.sentinela.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements ICustomerService {

	private final CustomerRepository customerRepository;

	@Override
	@Transactional
	public CustomerResponse createCustomer(CreateCustomerRequest request) {
		String email = normalizeEmail(request.email());
		String documentNumber = normalizeDocumentNumber(request.documentNumber());
		validateUniqueFields(email, documentNumber);

		Customer customer = new Customer(
				request.name().strip(),
				email,
				documentNumber,
				normalizePhoneNumber(request.phoneNumber()));

		return toResponse(customerRepository.save(customer));
	}

	@Override
	public List<CustomerResponse> getCustomers() {
		return customerRepository.findAllByOrderByNameAsc()
				.stream()
				.map(CustomerServiceImpl::toResponse)
				.toList();
	}

	@Override
	public CustomerResponse getCustomerById(UUID id) {
		return toResponse(findCustomerById(id));
	}

	@Override
	@Transactional
	public CustomerResponse updateCustomer(UUID id, UpdateCustomerRequest request) {
		Customer customer = findCustomerById(id);
		String email = normalizeEmail(request.email());
		String documentNumber = normalizeDocumentNumber(request.documentNumber());
		validateUniqueFields(email, documentNumber, id);

		customer.updateProfile(
				request.name().strip(),
				email,
				documentNumber,
				normalizePhoneNumber(request.phoneNumber()));

		return toResponse(customerRepository.save(customer));
	}

	@Override
	@Transactional
	public void deleteCustomer(UUID id) {
		Customer customer = findCustomerById(id);
		customerRepository.delete(customer);
	}

	private Customer findCustomerById(UUID id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException(id));
	}

	private void validateUniqueFields(String email, String documentNumber) {
		validateUniqueFields(email, documentNumber, null);
	}

	private void validateUniqueFields(String email, String documentNumber, UUID excludedId) {
		boolean emailExists = excludedId == null
				? customerRepository.existsByEmailIgnoreCase(email)
				: customerRepository.existsByEmailIgnoreCaseAndIdNot(email, excludedId);
		if (emailExists) {
			throw new CustomerAlreadyExistsException("email");
		}

		boolean documentNumberExists = excludedId == null
				? customerRepository.existsByDocumentNumber(documentNumber)
				: customerRepository.existsByDocumentNumberAndIdNot(documentNumber, excludedId);
		if (documentNumberExists) {
			throw new CustomerAlreadyExistsException("document number");
		}
	}

	private static String normalizeEmail(String email) {
		return email.strip().toLowerCase(Locale.ROOT);
	}

	private static String normalizeDocumentNumber(String documentNumber) {
		return documentNumber.strip();
	}

	private static String normalizePhoneNumber(String phoneNumber) {
		return phoneNumber == null || phoneNumber.isBlank() ? null : phoneNumber.strip();
	}

	private static CustomerResponse toResponse(Customer customer) {
		return new CustomerResponse(
				customer.getId(),
				customer.getName(),
				customer.getEmail(),
				customer.getDocumentNumber(),
				customer.getPhoneNumber(),
				customer.getCreatedAt(),
				customer.getUpdatedAt());
	}
}
