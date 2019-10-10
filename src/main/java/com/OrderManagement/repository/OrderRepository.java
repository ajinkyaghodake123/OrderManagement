package com.OrderManagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.OrderManagement.model.OrderModel;

@Repository
public interface OrderRepository extends MongoRepository<OrderModel, String> {

	OrderModel findByOrderId(String orderId);
	Boolean existsByOrderId(String orderId);
}
