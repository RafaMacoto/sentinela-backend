package com.sentinela.dto.fraudanalysis;

import com.sentinela.entity.fraudanalysis.RiskLevel;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record FraudAnalysisResponse(
		UUID id,
		UUID transactionId,
		BigDecimal riskScore,
		RiskLevel riskLevel,
		String reason,
		Instant createdAt) {
}
