package com.sentinela.repository.customer;

import com.sentinela.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

	boolean existsByEmailIgnoreCase(String email);

	boolean existsByEmailIgnoreCaseAndIdNot(String email, UUID id);

	boolean existsByDocumentNumber(String documentNumber);

	boolean existsByDocumentNumberAndIdNot(String documentNumber, UUID id);

	List<Customer> findAllByOrderByNameAsc();
}
