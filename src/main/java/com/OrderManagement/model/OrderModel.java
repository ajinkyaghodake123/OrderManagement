package com.OrderManagement.model;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Order")
public class OrderModel {
//	@Id
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
//	private UUID orderId;
	@Id
	private String orderId;
	private String customerId;
	private double actualAmount;
	private double discontedAmount;
	private double amountAfterDiscount;
	private double discountGivenInPercentage;
	private boolean isOrderReturned;
	private Date orderTime;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public double getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}
	public double getDiscontedAmount() {
		return discontedAmount;
	}
	public void setDiscontedAmount(double discontedAmount) {
		this.discontedAmount = discontedAmount;
	}
	public double getAmountAfterDiscount() {
		return amountAfterDiscount;
	}
	public void setAmountAfterDiscount(double amountAfterDiscount) {
		this.amountAfterDiscount = amountAfterDiscount;
	}
	public double getDiscountGivenInPercentage() {
		return discountGivenInPercentage;
	}
	public void setDiscountGivenInPercentage(double discountGivenInPercentage) {
		this.discountGivenInPercentage = discountGivenInPercentage;
	}
	public boolean isOrderReturned() {
		return isOrderReturned;
	}
	public void setOrderReturned(boolean isOrderReturned) {
		this.isOrderReturned = isOrderReturned;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public OrderModel(String orderId, String customerId, double actualAmount, double discontedAmount,
			double amountAfterDiscount, double discountGivenInPercentage, boolean isOrderReturned, Date orderTime) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.actualAmount = actualAmount;
		this.discontedAmount = discontedAmount;
		this.amountAfterDiscount = amountAfterDiscount;
		this.discountGivenInPercentage = discountGivenInPercentage;
		this.isOrderReturned = isOrderReturned;
		this.orderTime = orderTime;
	}
	public OrderModel(){}
	@Override
	public String toString() {
		return "OrderModel [orderId=" + orderId + ", customerId=" + customerId + ", actualAmount=" + actualAmount
				+ ", discontedAmount=" + discontedAmount + ", amountAfterDiscount=" + amountAfterDiscount
				+ ", discountGivenInPercentage=" + discountGivenInPercentage + ", isOrderReturned=" + isOrderReturned
				+ ", orderTime=" + orderTime + "]";
	}
	
	
}
