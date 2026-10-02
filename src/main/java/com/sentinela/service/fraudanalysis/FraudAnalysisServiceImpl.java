package com.sentinela.service.fraudanalysis;

import com.sentinela.dto.fraudanalysis.CreateFraudAnalysisRequest;
import com.sentinela.dto.fraudanalysis.FraudAnalysisResponse;
import com.sentinela.entity.fraudanalysis.FraudAnalysis;
import com.sentinela.entity.transaction.Transaction;
import com.sentinela.exception.fraudanalysis.FraudAnalysisNotFoundException;
import com.sentinela.exception.transaction.TransactionNotFoundException;
import com.sentinela.repository.fraudanalysis.FraudAnalysisRepository;
import com.sentinela.repository.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FraudAnalysisServiceImpl implements IFraudAnalysisService {

	private final FraudAnalysisRepository fraudAnalysisRepository;
	private final TransactionRepository transactionRepository;

	@Override
	@Transactional
	public FraudAnalysisResponse create(CreateFraudAnalysisRequest request) {
		Transaction transaction = transactionRepository.findById(request.transactionId())
				.orElseThrow(() -> new TransactionNotFoundException(request.transactionId()));
		FraudAnalysis analysis = new FraudAnalysis(
				transaction,
				request.riskScore(),
				request.riskLevel(),
				request.reason().strip());
		return toResponse(fraudAnalysisRepository.save(analysis));
	}

	@Override
	public List<FraudAnalysisResponse> getAll() {
		return fraudAnalysisRepository.findAllByOrderByCreatedAtDesc()
				.stream().map(FraudAnalysisServiceImpl::toResponse).toList();
	}

	@Override
	public FraudAnalysisResponse getById(UUID id) {
		return fraudAnalysisRepository.findById(id)
				.map(FraudAnalysisServiceImpl::toResponse)
				.orElseThrow(() -> new FraudAnalysisNotFoundException(id));
	}

	@Override
	public List<FraudAnalysisResponse> getByTransactionId(UUID transactionId) {
		if (!transactionRepository.existsById(transactionId)) {
			throw new TransactionNotFoundException(transactionId);
		}
		return fraudAnalysisRepository.findAllByTransactionIdOrderByCreatedAtDesc(transactionId)
				.stream().map(FraudAnalysisServiceImpl::toResponse).toList();
	}

	private static FraudAnalysisResponse toResponse(FraudAnalysis analysis) {
		return new FraudAnalysisResponse(
				analysis.getId(),
				analysis.getTransaction().getId(),
				analysis.getRiskScore(),
				analysis.getRiskLevel(),
				analysis.getReason(),
				analysis.getCreatedAt());
	}

}
