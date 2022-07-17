package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; 
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
/*
* Code owned by: Vishnu Prasad Preetham Kumar @creator 
*/

@SpringBootApplication
public class CrmApplication implements CommandLineRunner {
public static void main(String[] args) { 
  SpringApplication.run(CrmApplication.class, args);
}
@Autowired
private CustomerRepository customerRepository;
@Override
public void run(String... args) throws Exception {
}
}

