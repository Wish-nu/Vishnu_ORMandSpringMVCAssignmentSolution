CustomerServiceImpl.java
package com.example.demo.service.impl;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository; 
import com.example.demo.service.CustomerService; 
@Service
public class CustomerServiceImpl implements CustomerService {
  private CustomerRepository customerRepository;
  public CustomerServiceImpl(CustomerRepository customerRepository) {
  super();
  this.customerRepository = customerRepository;
}
  @Override
public List<Customer> getAllCustomers() {

// TODO Auto-generated method stub 
  return customerRepository.findAll();
}
  
  @Override
  public Customer saveCustomer(Customer customer) {
    return customerRepository.save(customer);
}
  @Override
public Customer getCustomerById(Long id) {
  return customerRepository.findById(id).get();
}
  @Override
  public Customer updateCustomer(Customer customer) {
    return customerRepository.save(customer);
}
  @Override
  public void deleteCustomerById(Long id) {
  // TODO Auto-generated method stub 
    customerRepository.deleteById(id);
  }
}
