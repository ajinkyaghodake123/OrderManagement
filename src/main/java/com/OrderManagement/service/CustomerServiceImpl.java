package com.OrderManagement.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.OrderManagement.model.CustomerModel;
import com.OrderManagement.repository.CustomerRepository;

@Service

public class CustomerServiceImpl implements CustomerService {

	private static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	  
	@Autowired
	CustomerRepository customerRepo;

	public String response;

	@Override
	public boolean createCustomer(CustomerModel customer) throws Exception {
		boolean orderStatus = false;

		if (!customerRepo.existsByEmailId(customer.getEmailId())) {
			customer.setCustomerID(UUID.randomUUID().toString());
			customerRepo.save(customer);
			logger.debug("customer saved with customer id: "+customer.getCustomerID()); 
			orderStatus = true;
		} else {
			logger.error("customer could not able to save with id :"+customer.getCustomerID() ) ;
			throw new Exception("customer is already present with this email id");
		}

		return orderStatus;
	}

	@Override
	public List<CustomerModel> getAllCutomers() {

		return customerRepo.findAll();
	}

	@Override
	public CustomerModel getCutomerById(String customerId) {
		return customerRepo.findByCustomerID(customerId);
	}

	@Override
	public String deleteCustomerById(String customerId) {
		if (customerRepo.existsByCustomerID(customerId)) {
			customerRepo.deleteByCustomerID(customerId);
			logger.debug("customer deleted");
			response = "Customer with id: " + customerId + " is deleted successfully";
		} else {
			logger.error("could not able to delete");
			response = "Customer with id: " + customerId + " is not found to delete";
		}
		return response;
	}

	@Override
	public String updateCustomerById(String customerId, CustomerModel customer) {
		if (customerRepo.existsByCustomerID(customerId) && customerRepo.existsByEmailId(customer.getEmailId())) {
			customer.setCustomerID(customerId);
			customerRepo.save(customer);
			response = "Customer with id: " + customerId + " is updated successfully";
		} else {
			response = "Customer with id: " + customerId + " is not found OR user with " + customer.getEmailId()
					+ " not present";
		}
		return response;
	}

	@Async
	public void updateCustomerAfterOrderPlaced(String customerId) {
		CustomerModel customer = customerRepo.findByCustomerID(customerId);
		// After creating new order increase orderNumber count by 1
		int orderPlced = customer.getTotalOrderPlaced() + 1;
		customer.setTotalOrderPlaced(orderPlced);
		// check for the category condition
		if (orderPlced > 9) {
			if (orderPlced > 19) {
				customer.setCategory("platinum");
				logger.debug("cutomer become platinum");
			} else {
				customer.setCategory("gold");
				logger.debug("cutomer become gold");
			}
		}
		// update he the customer with latest changes
		updateCustomerById(customerId, customer);
	}

	@Async
	public void updateCustomerAfterOrderReturned(String customerId) {
		CustomerModel customer = customerRepo.findByCustomerID(customerId);

		// After retruning order decrese orderNumber count by 1
		int orderPlced = customer.getTotalOrderPlaced() - 1;
		customer.setTotalOrderPlaced(orderPlced);
		// increase count of return oreder
		customer.setTotalOrderReturned(customer.getTotalOrderReturned() + 1);
		// check for the category condition
		if (orderPlced < 20) {
			if (orderPlced < 10) {
				customer.setCategory("regular");
				logger.debug("cutomer become regular");
			} else {
				customer.setCategory("gold");
				logger.debug("cutomer become gold");
			}
		}
		// update he the customer with latest changes
		updateCustomerById(customerId, customer);
	}
}
