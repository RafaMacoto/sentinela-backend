package com.sentinela.service.account;

import com.sentinela.dto.account.AccountResponse;
import com.sentinela.dto.account.CreateAccountRequest;
import com.sentinela.dto.account.UpdateAccountRequest;

import java.util.List;
import java.util.UUID;

public interface IAccountService {

	AccountResponse create(CreateAccountRequest request);

	List<AccountResponse> getAll();

	AccountResponse getById(UUID id);

	AccountResponse update(UUID id, UpdateAccountRequest request);

	void delete(UUID id);
}
