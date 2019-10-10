package com.OrderManagement;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.Assert;

import com.OrderManagement.model.CustomerModel;
import com.OrderManagement.repository.CustomerRepository;
import com.OrderManagement.service.CustomerService;
import com.OrderManagement.service.CustomerServiceImpl;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceTest {


	@Mock
	CustomerRepository customerRepositoryMock;

	@InjectMocks
	CustomerServiceImpl customerService;

	private String customerId = "12345";

	@Test
	public void testForCreateCustomer() throws Exception {
		createCustomerTest(customerId);
	}
	
	
	public void createCustomerTest(String customerId) throws Exception {
		CustomerModel customerModel = new CustomerModel();
		customerModel.setCustomerID(customerId);
		Mockito.when(customerRepositoryMock.existsByEmailId(Mockito.anyString())).thenReturn(false);
		Mockito.when(customerRepositoryMock.save(customerModel)).thenReturn(mockCustomerModel());

		boolean customerOutput = customerService.createCustomer(customerModel); 
		Assert.assertTrue(customerOutput);
	}
	
	@Test
	public void testToGetAllCustomers() {
		List<CustomerModel> list = new ArrayList<CustomerModel>();
		Mockito.when(customerRepositoryMock.findAll()).thenReturn(customerList());
		list = customerService.getAllCutomers();
		Assert.assertNotNull(list);
	}

	@Test
	public void testForUpdateCustomer() {
		CustomerModel customerModel = new CustomerModel();
		customerModel.setPhoneNo(789456123);
		customerModel.setEmailId("abc@gmail.com");
		
		String output = customerService.updateCustomerById(customerId, customerModel);
		Assert.assertNotNull(output);
	}
	
	@Test
	public void testUpdateCustoomerAfterOrderPlced() {
		CustomerService mockCustomer = Mockito.mock(CustomerService.class);
		CustomerModel customerModel = new CustomerModel();
		customerModel.setEmailId("abc@gmail.com");
		Mockito.when(customerRepositoryMock.findByCustomerID(customerId)).thenReturn(mockCustomerModel());
		Mockito.doNothing().when(mockCustomer).updateCustomerAfterOrderPlaced(customerId);
		mockCustomer.updateCustomerAfterOrderPlaced(customerId); 
		Mockito.verify(mockCustomer, Mockito.times(1)).updateCustomerAfterOrderPlaced(customerId);
		
	}

	private CustomerModel mockCustomerModel() {
		CustomerModel customer = new CustomerModel();
		customer.setCategory("regular");
		customer.setCustomerID(customerId);
		customer.setEmailId("abc@gmail.com");
		customer.setPhoneNo(1231546789);
		customer.setTotalOrderPlaced(9);
		customer.setTotalOrderReturned(0);
		return customer;

	}
	private List<CustomerModel> customerList() {
		CustomerModel customer = mockCustomerModel();
		List<CustomerModel> list = new ArrayList<CustomerModel>();
		list.add(customer);
		return list;
	}

}
