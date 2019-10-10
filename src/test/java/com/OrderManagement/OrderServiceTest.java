package com.OrderManagement;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.Assert;

import com.OrderManagement.model.CustomerModel;
import com.OrderManagement.model.OrderModel;
import com.OrderManagement.repository.CustomerRepository;
import com.OrderManagement.repository.OrderRepository;
import com.OrderManagement.service.CustomerService;
import com.OrderManagement.service.OrderServiceImpl;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceTest {

	@Mock
	OrderRepository orderRepositoryMock;

	@Mock
	CustomerRepository customerRepoMock;

	@InjectMocks
	OrderServiceImpl orderService;

	@Mock
	CustomerService customerService;

	private String orderId = "12345";
	private String customerId = "12345";

	@Test
	public void testForCretaeOrder() throws Exception {
		OrderModel order = mockOrder();
		Mockito.when(orderRepositoryMock.existsByOrderId(orderId)).thenReturn(false);
		Mockito.when(orderRepositoryMock.save(order)).thenReturn(mockOrder());
		boolean output = orderService.createOrder(order);
		Assert.assertTrue(output);
	}

	@Test
	public void testForGetAllOrders() {
		Mockito.when(orderRepositoryMock.findAll()).thenReturn(mockListOrder());
		List<OrderModel> orders = orderService.getAllOrders();
		Assert.assertNotNull(orders);
	}

	@Test
	public void testForCheckForDiscount() {
		OrderModel order = mockOrder();
		Mockito.when(customerService.getCutomerById(order.getCustomerId())).thenReturn(mockCustomerModel());
		OrderModel outputOrder = orderService.checkForDiscount(order);
		Assert.assertNotNull(outputOrder);
	}
	
	@Test
	public void testForreturnOrder() {
		
		Mockito.when(orderRepositoryMock.findByOrderId(orderId)).thenReturn(mockOrder());
		Mockito.when(orderRepositoryMock.save(mockOrder())).thenReturn(mockOrder());
		Mockito.when(orderRepositoryMock.existsByOrderId(orderId)).thenReturn(true);
		String outputResponse = orderService.returnOrder(orderId);
		Assert.assertNotNull(outputResponse);
	}

	private OrderModel mockOrder() {
		return new OrderModel(orderId, customerId, 200, 20, 180, 10, false, null);
	}

	private List<OrderModel> mockListOrder() {
		List<OrderModel> mocklistOrder = new ArrayList<OrderModel>();
		mocklistOrder.add(mockOrder());
		return mocklistOrder;
	}

	private CustomerModel mockCustomerModel() {
		CustomerModel customer = new CustomerModel();
		customer.setCategory("gold");
		customer.setCustomerID(customerId);
		customer.setEmailId("abc@gmail.com");
		customer.setPhoneNo(1231546789);
		customer.setTotalOrderPlaced(11);
		customer.setTotalOrderReturned(0);
		return customer;

	}
}
