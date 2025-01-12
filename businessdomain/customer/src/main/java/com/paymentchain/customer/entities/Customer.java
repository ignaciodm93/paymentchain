package com.paymentchain.customer.entities;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class Customer {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;
	private String name;
	private String phone;
	private String iban;
	private String surename;
	private String address;
	private String code;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CustomerProduct> products;
	@Transient
	private List<?> transactions;

}
