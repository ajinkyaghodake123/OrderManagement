package com.OrderManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.OrderManagement.model.CustomerModel;

@Service
public interface CustomerService {
	boolean createCustomer(CustomerModel customer) throws Exception;
	List<CustomerModel> getAllCutomers();
	CustomerModel getCutomerById(String id);
	String deleteCustomerById(String id);
	String updateCustomerById(String id , CustomerModel customer);
	void updateCustomerAfterOrderPlaced(String id);
	void updateCustomerAfterOrderReturned(String id);
}
