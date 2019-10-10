package com.OrderManagement.service;

import java.util.List;

import com.OrderManagement.model.OrderModel;

public interface OrderService {

	boolean createOrder(OrderModel oreder) throws Exception;
	List<OrderModel> getAllOrders();
	OrderModel getOrderById(String id);
	String updateOrderById(String id, OrderModel order);
	OrderModel checkForDiscount(OrderModel order);
	String returnOrder(String orderId);
}
