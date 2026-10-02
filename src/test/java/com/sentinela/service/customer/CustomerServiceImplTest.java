package com.sentinela.service.customer;

import com.sentinela.dto.customer.CreateCustomerRequest;
import com.sentinela.dto.customer.CustomerResponse;
import com.sentinela.entity.customer.Customer;
import com.sentinela.exception.customer.CustomerAlreadyExistsException;
import com.sentinela.repository.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerServiceImpl customerService;

	@Test
	void createCustomerNormalizesEmailAndProfileFields() {
		CreateCustomerRequest request = new CreateCustomerRequest(
				"  Ada Lovelace  ",
				"  ADA@EXAMPLE.COM ",
				"  customer-123  ",
				"  +1 555 0100  ");
		when(customerRepository.save(any(Customer.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		CustomerResponse response = customerService.createCustomer(request);

		assertEquals("Ada Lovelace", response.name());
		assertEquals("ada@example.com", response.email());
		assertEquals("customer-123", response.documentNumber());
		assertEquals("+1 555 0100", response.phoneNumber());
		verify(customerRepository).save(any(Customer.class));
	}

	@Test
	void createCustomerRejectsDuplicateEmail() {
		CreateCustomerRequest request = new CreateCustomerRequest(
				"Ada Lovelace",
				"ada@example.com",
				"customer-123",
				null);
		when(customerRepository.existsByEmailIgnoreCase("ada@example.com")).thenReturn(true);

		assertThrows(CustomerAlreadyExistsException.class, () -> customerService.createCustomer(request));

		verify(customerRepository, never()).save(any(Customer.class));
	}
}
