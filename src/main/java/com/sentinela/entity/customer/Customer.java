package com.sentinela.entity.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(
		name = "customers",
		uniqueConstraints = {
				@UniqueConstraint(name = "uk_customers_email", columnNames = "email"),
				@UniqueConstraint(name = "uk_customers_document_number", columnNames = "document_number")
		})
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 120)
	private String name;

	@Column(nullable = false, length = 254)
	private String email;

	@Column(name = "document_number", nullable = false, length = 32)
	private String documentNumber;

	@Column(name = "phone_number", length = 32)
	private String phoneNumber;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	protected Customer() {
	}

	public Customer(String name, String email, String documentNumber, String phoneNumber) {
		this.name = name;
		this.email = email;
		this.documentNumber = documentNumber;
		this.phoneNumber = phoneNumber;
	}

	public void updateProfile(String name, String email, String documentNumber, String phoneNumber) {
		this.name = name;
		this.email = email;
		this.documentNumber = documentNumber;
		this.phoneNumber = phoneNumber;
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
