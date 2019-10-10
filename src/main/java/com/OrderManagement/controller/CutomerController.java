package com.OrderManagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.model.CustomerModel;
import com.OrderManagement.service.CustomerService;

@EnableScheduling
@RestController
@RequestMapping({ "/customer" })
public class CutomerController {

	@Autowired
	CustomerService customerService;

	private String response = null;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<CustomerModel> getAllCutomers() {
		return customerService.getAllCutomers();

	}

	// REGISTER customer
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> create(@RequestBody CustomerModel customer) throws Exception {
		ResponseEntity<String> responseEntity = null;
		System.out.println("customer in controller--> " + customer);
		System.out.println(customer.getName());
		try {
			boolean orderstatus = customerService.createCustomer(customer);
			if (orderstatus) {
				response = "customer has created successfully";
			}
			responseEntity = ResponseEntity.status(201).body(response);

		} catch (Exception ex) {
			responseEntity = ResponseEntity.status(409).body(ex.getMessage());
		}
	
		return responseEntity;

	}

	// Find Customer by customer ID
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CustomerModel getCutomerById(@PathVariable("id") String customerId) {
		return customerService.getCutomerById(customerId);

	}

	// Delete customer By customerId
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> deleteCutomerById(@PathVariable("id") String customerId) {
		String response = customerService.deleteCustomerById(customerId);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	// update customer by customer ID
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateCutomerById(@PathVariable("id") String customerId, @RequestBody CustomerModel customer) {
		String response = customerService.updateCustomerById(customerId, customer);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/schedular", method = RequestMethod.GET)
	@Scheduled(cron = "0 30 15 * * ?")
	public void scheduleTaskUsingCronExpression() {
	  
		List<CustomerModel> customerModel = getAllCutomers() ;
		customerModel.stream().forEach(customer->{
			if(customer.getTotalOrderPlaced() == 9) {
				System.out.println("sent mail to customer");
				System.out.println("if you place 1 more order you will become gold customer and will be benifited flat 10% discount on all the orders.");
			}
			else if(customer.getTotalOrderPlaced() == 19) {
				System.out.println("sent mail to customer");
				System.out.println("if you place 1 more order you will become platinum customer and will be benifited flat 20% discount on all the orders.");
			}
		});
	}
}
