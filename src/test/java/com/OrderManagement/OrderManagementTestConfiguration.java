package com.OrderManagement;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.OrderManagement.service.CustomerService;
import com.OrderManagement.service.CustomerServiceImpl;
import com.OrderManagement.service.OrderService;
import com.OrderManagement.service.OrderServiceImpl;

@Profile("test")
@Configuration
public class OrderManagementTestConfiguration {

	@Bean
	public CustomerService customerService() {
		return Mockito.mock(CustomerServiceImpl.class);
	}
	
	@Bean
	public OrderService orderService() {
		return Mockito.mock(OrderServiceImpl.class);
	}
}
