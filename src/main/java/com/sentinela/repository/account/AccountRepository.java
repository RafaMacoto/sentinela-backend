package com.sentinela.repository.account;

import com.sentinela.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

	boolean existsByAccountNumberIgnoreCase(String accountNumber);

	boolean existsByAccountNumberIgnoreCaseAndIdNot(String accountNumber, UUID id);

	boolean existsByIdAndCustomerId(UUID id, UUID customerId);

	List<Account> findAllByOrderByAccountNumberAsc();
}
