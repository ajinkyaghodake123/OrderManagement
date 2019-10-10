package com.OrderManagement.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrderManagement.model.CustomerModel;
import com.OrderManagement.model.OrderModel;
import com.OrderManagement.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	CustomerService customerService;

	private static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	public String response = null;

	@Override
	public boolean createOrder(OrderModel order) throws Exception {
		boolean orderStatus = false;
		order.setOrderId(UUID.randomUUID().toString());
		if(!orderRepo.existsById(order.getOrderId())) {
			order.setOrderTime(new Date());
		 	orderRepo.save(order);
		 	logger.debug("successfully created order ");
		 	orderStatus = true;
		}
		else {
			logger.debug("could not able to create order");
			throw new Exception("Unable to create your order. Please try again");
		}
		return orderStatus;
	}

	@Override
	public List<OrderModel> getAllOrders() {
		return orderRepo.findAll();
	}

	@Override
	public OrderModel getOrderById(String orderId) {
		return orderRepo.findByOrderId(orderId);
	}

	@Override
	public String updateOrderById(String orderId, OrderModel order) {
		if (orderRepo.existsByOrderId(orderId)) {
			OrderModel oldOrder = orderRepo.findByOrderId(orderId);
			if(new Double(order.getActualAmount()) != null) {
			oldOrder.setActualAmount(order.getActualAmount());
			}
			if(new Double(order.getDiscontedAmount()) != null) {
			oldOrder.setDiscontedAmount(order.getDiscontedAmount());
			}
			if(new Double(order.getDiscountGivenInPercentage()) != null) {
			oldOrder.setDiscountGivenInPercentage(order.getDiscountGivenInPercentage());
			}
			oldOrder.setOrderReturned(order.isOrderReturned());

			orderRepo.save(oldOrder);
			logger.debug("order udated with order id:"+order.getOrderId() );
			response = "Oder with id: " + orderId + " is updated successfully";
			System.out.println(response);
		} else {
			response = "Order with id: " + orderId + " is not found";
		}
		return response;
	}

	@Override
	public OrderModel checkForDiscount(OrderModel order) {
		CustomerModel customer = customerService.getCutomerById(order.getCustomerId());
		OrderModel orderAfterCalculation = new OrderModel();
		switch (customer.getCategory()) {
		case "gold":
			orderAfterCalculation = discountforGold(order);
			break;
		case "platinum":
			orderAfterCalculation = discountForPlatnum(order);
			break;
		case "regular":
			orderAfterCalculation = discountForRegular(order);
			break;
		default:
			break;
		}
		return orderAfterCalculation;
	}

	private OrderModel discountforGold(OrderModel order) {
		return calculateDiscount(order, 10);
	}

	private OrderModel discountForPlatnum(OrderModel order) {
		return calculateDiscount(order, 20);
	}

	private OrderModel discountForRegular(OrderModel order) {
		return calculateDiscount(order, 0);
	}

	private OrderModel calculateDiscount(OrderModel order, double percentageDiscount) {
		double discountedAmount = 0;
		if (percentageDiscount > 0) {
			discountedAmount = order.getActualAmount() * percentageDiscount / 100;
		}
		double priceAfterDiscount = order.getActualAmount() - discountedAmount;
		order.setDiscontedAmount(discountedAmount);
		order.setAmountAfterDiscount(priceAfterDiscount);
		order.setDiscountGivenInPercentage(percentageDiscount);
		return order;
	}

	@Override
	public String returnOrder(String orderId) {
		OrderModel order = orderRepo.findByOrderId(orderId);
		System.out.println("return order = "+ order);
		if(!order.equals(null)) {
			
			System.out.println("order to be return -- "+order);
			// set the order return flag
			order.setOrderReturned(true);
			updateOrderById(orderId, order);
			// update the customer (total orders, total returns, check category)
			customerService.updateCustomerAfterOrderReturned(order.getCustomerId());
			response = "Request for retrun Order with id: " + orderId + " has been placed";
			System.out.println(response);
		}
		response = "Request with id : "+orderId+" is failed";
		return response;
	}
}
