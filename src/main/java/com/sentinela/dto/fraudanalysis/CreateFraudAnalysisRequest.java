package com.sentinela.dto.fraudanalysis;

import com.sentinela.entity.fraudanalysis.RiskLevel;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateFraudAnalysisRequest(
		@NotNull UUID transactionId,
		@NotNull @DecimalMin("0.00") @DecimalMax("100.00") BigDecimal riskScore,
		@NotNull RiskLevel riskLevel,
		@NotBlank @Size(max = 2000) String reason) {
}
