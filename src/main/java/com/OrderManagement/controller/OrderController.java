package com.OrderManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.model.OrderModel;
import com.OrderManagement.service.CustomerService;
import com.OrderManagement.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderService orderService;

	@Autowired
	CustomerService customerService;

	private String response = null;

	// To place the order
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<String> CreateOrder(@RequestBody OrderModel order) {
		ResponseEntity<String> responseEntity = null;

		OrderModel orderDetailsAfterCheckingDiscount = orderService.checkForDiscount(order);
		try {
			boolean orderStatus = orderService.createOrder(orderDetailsAfterCheckingDiscount);
			if (orderStatus) {
				response = "Oder has created successfully";
			}
			responseEntity = ResponseEntity.status(201).body(response);
			customerService.updateCustomerAfterOrderPlaced(order.getCustomerId());
		} catch (Exception ex) {
			responseEntity = ResponseEntity.status(409).body(ex.getMessage());
		}
		return responseEntity;
	}

	// get all orders
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<OrderModel> getAllOrders() {
		return orderService.getAllOrders();

	}

	// get order by order ID
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public OrderModel getOrderById(@PathVariable("id") String orderId) {
		return orderService.getOrderById(orderId);

	}

	// updaet order
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateOrderById(@PathVariable("id") String customerId,
			@RequestBody OrderModel order) {
		String response = orderService.updateOrderById(customerId, order);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	// Return order
	@RequestMapping(value = "/return/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> retrunOrder(@PathVariable("id") String orderId) {
		String response = orderService.returnOrder(orderId);
		return new ResponseEntity<String>(response, HttpStatus.OK);

	}
}
