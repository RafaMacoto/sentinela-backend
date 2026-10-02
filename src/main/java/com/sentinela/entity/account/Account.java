package com.sentinela.entity.account;

import com.sentinela.entity.customer.Customer;
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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(name = "accounts", uniqueConstraints = {
		@UniqueConstraint(name = "uk_accounts_account_number", columnNames = "account_number")
})
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_accounts_customer"))
	private Customer customer;

	@Column(name = "account_number", nullable = false, length = 34)
	private String accountNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "account_type", nullable = false, length = 20)
	private AccountType accountType;

	@Column(nullable = false, length = 3)
	private String currency;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	protected Account() {
	}

	public Account(Customer customer, String accountNumber, AccountType accountType, String currency) {
		this.customer = customer;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.currency = currency;
	}

	public void update(Customer customer, String accountNumber, AccountType accountType, String currency) {
		this.customer = customer;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.currency = currency;
	}

	@PrePersist
	private void setCreationTimestamps() {
		Instant now = Instant.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	private void updateTimestamp() {
		updatedAt = Instant.now();
	}
}
