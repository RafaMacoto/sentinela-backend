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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_ref_id", foreignKey = @ForeignKey(name = "fk_transactions_customer"))
	private Customer customer;

	// Keep the original ID columns intact for transactions created before domain relationships existed.
	@Column(name = "customer_id", nullable = false)
	private UUID customerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;

	@Column(nullable = false)
	private UUID recipientId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_ref_id", foreignKey = @ForeignKey(name = "fk_transactions_device"))
	private Device device;

	@Column(name = "device_id", nullable = false)
	private UUID deviceId;

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
		this.customerId = customer.getId();
		this.account = account;
		this.recipientId = recipientId;
		this.device = device;
		this.deviceId = device.getId();
		this.amount = amount;
		this.timestamp = timestamp;
		this.newDevice = newDevice;
		this.newRecipient = newRecipient;
	}


}
