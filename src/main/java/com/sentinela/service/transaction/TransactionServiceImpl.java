package com.sentinela.service.transaction;

import com.sentinela.dto.transaction.TransactionRequest;
import com.sentinela.dto.transaction.TransactionResponse;
import com.sentinela.entity.account.Account;
import com.sentinela.entity.customer.Customer;
import com.sentinela.entity.device.Device;
import com.sentinela.entity.transaction.Transaction;
import com.sentinela.exception.account.AccountNotFoundException;
import com.sentinela.exception.customer.CustomerNotFoundException;
import com.sentinela.exception.device.DeviceNotFoundException;
import com.sentinela.exception.transaction.TransactionRelationshipException;
import com.sentinela.repository.account.AccountRepository;
import com.sentinela.repository.customer.CustomerRepository;
import com.sentinela.repository.device.DeviceRepository;
import com.sentinela.repository.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;
	private final DeviceRepository deviceRepository;

	@Override
	@Transactional
	public TransactionResponse create(TransactionRequest request) {
		Customer customer = customerRepository.findById(request.customerId())
				.orElseThrow(() -> new CustomerNotFoundException(request.customerId()));
		Device device = deviceRepository.findById(request.deviceId())
				.orElseThrow(() -> new DeviceNotFoundException(request.deviceId()));
		if (!deviceRepository.existsByIdAndCustomerId(device.getId(), customer.getId())) {
			throw new TransactionRelationshipException("The device does not belong to the specified customer.");
		}
		Account account = findOptionalAccount(request.accountId());
		if (account != null && !accountRepository.existsByIdAndCustomerId(account.getId(), customer.getId())) {
			throw new TransactionRelationshipException("The account does not belong to the specified customer.");
		}

		Transaction transaction = new Transaction(
				customer,
				account,
				request.recipientId(),
				device,
				request.amount(),
				request.timestamp(),
				request.newDevice(),
				request.newRecipient());

		return toResponse(transactionRepository.save(transaction));
	}

	private Account findOptionalAccount(UUID accountId) {
		if (accountId == null) {
			return null;
		}
		return accountRepository.findById(accountId)
				.orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	private static TransactionResponse toResponse(Transaction transaction) {
		return new TransactionResponse(
				transaction.getId(),
				transaction.getCustomer().getId(),
				transaction.getAccount() == null ? null : transaction.getAccount().getId(),
				transaction.getRecipientId(),
				transaction.getDevice().getId(),
				transaction.getAmount(),
				transaction.getTimestamp(),
				transaction.isNewDevice(),
				transaction.isNewRecipient());
	}
}
