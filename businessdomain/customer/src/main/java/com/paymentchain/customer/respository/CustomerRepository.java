package com.paymentchain.customer.respository;

import com.paymentchain.customer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query("select c from customer c where c.code = ?1")
	public Customer findByCode(String code);
	
	@Query("select c from customer c where c.iban = ?1")
	public Customer findByAccount(String iban);
}
