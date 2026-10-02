package com.sentinela.service.account;

import com.sentinela.dto.account.AccountResponse;
import com.sentinela.dto.account.CreateAccountRequest;
import com.sentinela.dto.account.UpdateAccountRequest;
import com.sentinela.entity.account.Account;
import com.sentinela.entity.customer.Customer;
import com.sentinela.exception.account.AccountAlreadyExistsException;
import com.sentinela.exception.account.AccountNotFoundException;
import com.sentinela.exception.customer.CustomerNotFoundException;
import com.sentinela.repository.account.AccountRepository;
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
public class AccountServiceImpl implements IAccountService {

	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;

	@Override
	@Transactional
	public AccountResponse create(CreateAccountRequest request) {
		String accountNumber = normalizeAccountNumber(request.accountNumber());
		if (accountRepository.existsByAccountNumberIgnoreCase(accountNumber)) {
			throw new AccountAlreadyExistsException();
		}
		Account account = new Account(
				findCustomer(request.customerId()),
				accountNumber,
				request.accountType(),
				normalizeCurrency(request.currency()));
		return toResponse(accountRepository.save(account));
	}

	@Override
	public List<AccountResponse> getAll() {
		return accountRepository.findAllByOrderByAccountNumberAsc()
				.stream().map(AccountServiceImpl::toResponse).toList();
	}

	@Override
	public AccountResponse getById(UUID id) {
		return toResponse(findAccount(id));
	}

	@Override
	@Transactional
	public AccountResponse update(UUID id, UpdateAccountRequest request) {
		Account account = findAccount(id);
		String accountNumber = normalizeAccountNumber(request.accountNumber());
		if (accountRepository.existsByAccountNumberIgnoreCaseAndIdNot(accountNumber, id)) {
			throw new AccountAlreadyExistsException();
		}
		account.update(
				findCustomer(request.customerId()),
				accountNumber,
				request.accountType(),
				normalizeCurrency(request.currency()));
		return toResponse(accountRepository.save(account));
	}

	@Override
	@Transactional
	public void delete(UUID id) {
		accountRepository.delete(findAccount(id));
	}

	private Account findAccount(UUID id) {
		return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
	}

	private Customer findCustomer(UUID id) {
		return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
	}

	private static String normalizeAccountNumber(String value) {
		return value.strip();
	}

	private static String normalizeCurrency(String value) {
		return value.strip().toUpperCase(Locale.ROOT);
	}

	private static AccountResponse toResponse(Account account) {
		return new AccountResponse(
				account.getId(),
				account.getCustomer().getId(),
				account.getAccountNumber(),
				account.getAccountType(),
				account.getCurrency(),
				account.getCreatedAt(),
				account.getUpdatedAt());
	}
}
