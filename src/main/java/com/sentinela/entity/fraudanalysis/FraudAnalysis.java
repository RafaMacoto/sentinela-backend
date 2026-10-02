package com.sentinela.entity.fraudanalysis;

import com.sentinela.entity.transaction.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(name = "fraud_analyses")
public class FraudAnalysis {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "transaction_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_fraud_analyses_transaction"))
	private Transaction transaction;

	@Column(name = "risk_score", nullable = false, precision = 5, scale = 2)
	private BigDecimal riskScore;

	@Enumerated(EnumType.STRING)
	@Column(name = "risk_level", nullable = false, length = 20)
	private RiskLevel riskLevel;

	@Column(nullable = false, length = 2000)
	private String reason;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	protected FraudAnalysis() {
	}

	public FraudAnalysis(Transaction transaction, BigDecimal riskScore, RiskLevel riskLevel, String reason) {
		this.transaction = transaction;
		this.riskScore = riskScore;
		this.riskLevel = riskLevel;
		this.reason = reason;
	}

	@PrePersist
	private void setCreationTimestamp() {
		createdAt = Instant.now();
	}
}
