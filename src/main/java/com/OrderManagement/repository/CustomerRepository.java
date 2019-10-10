package com.OrderManagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.OrderManagement.model.CustomerModel;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerModel, Integer> {

	public CustomerModel findByCustomerID(String customerId);
	public Boolean existsByCustomerID(String customerId);
	public void deleteByCustomerID(String customerId);
	Boolean existsByEmailId(String emailId);
}
