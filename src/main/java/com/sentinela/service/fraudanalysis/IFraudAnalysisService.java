package com.sentinela.service.fraudanalysis;

import com.sentinela.dto.fraudanalysis.CreateFraudAnalysisRequest;
import com.sentinela.dto.fraudanalysis.FraudAnalysisResponse;

import java.util.List;
import java.util.UUID;

public interface IFraudAnalysisService {

	FraudAnalysisResponse create(CreateFraudAnalysisRequest request);

	List<FraudAnalysisResponse> getAll();

	FraudAnalysisResponse getById(UUID id);

	List<FraudAnalysisResponse> getByTransactionId(UUID transactionId);
}
