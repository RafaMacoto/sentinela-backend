package com.sentinela.entity.transaction;

import com.sentinela.entity.account.Account;
import com.sentinela.entity.customer.Customer;
import com.sentinela.entity.device.Device;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	// Legacy transaction rows may reference customers/devices that are not in their tables.
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(
			name = "customer_id",
			nullable = false,
			foreignKey = @ForeignKey(jakarta.persistence.ConstraintMode.NO_CONSTRAINT))
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;

	@Column(nullable = false)
	private UUID recipientId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(
			name = "device_id",
			nullable = false,
			foreignKey = @ForeignKey(jakarta.persistence.ConstraintMode.NO_CONSTRAINT))
	private Device device;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(name = "transaction_timestamp", nullable = false)
	private LocalDateTime timestamp;

	@Column(nullable = false)
	private boolean newDevice;

	@Column(nullable = false)
	private boolean newRecipient;

	protected Transaction() {
	}

	public Transaction(
			Customer customer,
			Account account,
			UUID recipientId,
			Device device,
			BigDecimal amount,
			LocalDateTime timestamp,
			boolean newDevice,
			boolean newRecipient) {
		this.customer = customer;
		this.account = account;
		this.recipientId = recipientId;
		this.device = device;
		this.amount = amount;
		this.timestamp = timestamp;
		this.newDevice = newDevice;
		this.newRecipient = newRecipient;
	}

}
