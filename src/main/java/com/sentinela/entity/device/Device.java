package com.sentinela.entity.device;

import com.sentinela.entity.customer.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "devices", uniqueConstraints = {
		@UniqueConstraint(
				name = "uk_devices_customer_identifier",
				columnNames = {"customer_id", "device_identifier"})
})
public class Device {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_devices_customer"))
	private Customer customer;

	@Column(name = "device_identifier", nullable = false, length = 160)
	private String deviceIdentifier;

	@Column(name = "device_type", nullable = false, length = 40)
	private String deviceType;

	@Column(nullable = false)
	private boolean trusted;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	protected Device() {
	}

	public Device(Customer customer, String deviceIdentifier, String deviceType, boolean trusted) {
		this.customer = customer;
		this.deviceIdentifier = deviceIdentifier;
		this.deviceType = deviceType;
		this.trusted = trusted;
	}

	public void update(Customer customer, String deviceIdentifier, String deviceType, boolean trusted) {
		this.customer = customer;
		this.deviceIdentifier = deviceIdentifier;
		this.deviceType = deviceType;
		this.trusted = trusted;
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
