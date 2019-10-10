package com.OrderManagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Customer")
public class CustomerModel {
	@Id
	private String emailId;
	private String customerID;
	private String name;
	private long phoneNo;
	private String category;
	private int totalOrderPlaced;
	private int totalOrderReturned;
	
	
	

	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getTotalOrderPlaced() {
		return totalOrderPlaced;
	}
	public void setTotalOrderPlaced(int totalOrderPlaced) {
		this.totalOrderPlaced = totalOrderPlaced;
	}
	public int getTotalOrderReturned() {
		return totalOrderReturned;
	}
	public void setTotalOrderReturned(int totalOrderReturned) {
		this.totalOrderReturned = totalOrderReturned;
	}
	@Override
	public String toString() {
		return "CustomerModel [emailId=" + emailId + ", customerID=" + customerID + ", name=" + name + ", phoneNo="
				+ phoneNo + ", category=" + category + ", totalOrderPlaced=" + totalOrderPlaced
				+ ", totalOrderReturned=" + totalOrderReturned + "]";
	}
	
}
