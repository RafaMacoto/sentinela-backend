package com.sentinela.repository.fraudanalysis;

import com.sentinela.entity.fraudanalysis.FraudAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FraudAnalysisRepository extends JpaRepository<FraudAnalysis, UUID> {

	List<FraudAnalysis> findAllByOrderByCreatedAtDesc();

	List<FraudAnalysis> findAllByTransactionIdOrderByCreatedAtDesc(UUID transactionId);
}
