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

CustomerController.java
package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping; 
import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;

@Controller
public class CustomerController {
private CustomerService customerService;
public CustomerController(CustomerService customerService) { 
  super();
this.customerService = customerService;
}
  
//handler method to handle list students and return mode and view 
@GetMapping("/customers")
public String listCustomers(Model model) {
model.addAttribute("customers",customerService.getAllCustomers()); 
return "customers";
}

@GetMapping("/customers/new")
public String createCustomerForm(Model model) {
//create customer object to hold customer form data 
  Customer customer=new Customer(); 
  model.addAttribute("customer",customer);
return "create_customer";
}

@PostMapping("/customers")
public String saveCustomer(@ModelAttribute("customer") Customer customer) {
customerService.saveCustomer(customer); 
return "redirect:/customers";
  }
  
@GetMapping("/customers/edit/{id}")
public String editCustomerForm(@PathVariable Long id,Model model) {
model.addAttribute("customer",customerService.getCustomerById(id)); 
  return "edit_customer";
  }
  
@PostMapping("/customers/{id}")
public String updateCustomer(@PathVariable Long id,
@ModelAttribute("customer") Customer customer, Model model) {
  
  //get customer from database by id
Customer existingCustomer=customerService.getCustomerById(id); 
existingCustomer.setFirstName(customer.getFirstName()); 
existingCustomer.setLastName(customer.getLastName()); 
existingCustomer.setEmail(customer.getEmail());
customerService.updateCustomer(existingCustomer); 
  return "redirect:/customers";
  }
  
//handler method to handle delete student request 
  @GetMapping("/customers/{id}")
  public String deleteStudent(@PathVariable Long id) {
  customerService.deleteCustomerById(id); 
    return "redirect:/customers";
    } 
}

Customer.java
package com.example.demo.entity; 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue; 
import javax.persistence.GenerationType; 
import javax.persistence.Id;
import javax.persistence.Table; 
@Entity 
@Table(name="customer") 
public class Customer {
@Id
@GeneratedValue(strategy =GenerationType.IDENTITY)
private Long id;
@Column(name="first_name", nullable=false)
private String firstName;
@Column(name="last_name")
private String lastName;
@Column(name="email")
private String email;
public Customer() {
}
  public Customer(String firstName, String lastName, String email) {
    super();
    this.firstName = firstName; 
    this.lastName = lastName; 
    this.email = email;
}
  public Long getId() {
  return id; 
  }
  public void setId(Long id) {
    this.id = id;
}
public String getFirstName() {
return firstName;
}
  public void setFirstName(String firstName) {
    this.firstName = firstName;
}
  public String getLastName() {
    return lastName;
}
  public void setLastName(String lastName) {
    this.lastName = lastName;
}
  public String getEmail() {
    return email; 
}
  public void setEmail(String email) { 
    this.email = email;
}
}

CustomerRepository.java
package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Customer;
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

CustomerService.java
package com.example.demo.service;
import java.util.List;
import com.example.demo.entity.Customer; 
public interface CustomerService {
  List<Customer> getAllCustomers();
  Customer saveCustomer(Customer customer); 
  Customer getCustomerById(Long id);
  Customer updateCustomer(Customer customer); 
  void deleteCustomerById(Long id);
}

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

application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/crm?useSSL=false&serverTimezone= UTC&useLegacyDatetimeCode=false
spring.datasource.username=root
spring.datasource.password=root

#Hibernate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect 

#Hibernate auto ddl
spring.jpa.hibernate.ddl-auto=update 
logging.level.org.org.hibernate.SQL=DEBUG


create_customer.html

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"> <head>
<meta charset="ISO-8859-1">
<title>Customer Relationship Management</title> <link rel="stylesheet"
  href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
  integrity="sha384- Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
  crossorigin="anonymous">

</head>
<body>
<nav class="navbar navbar-expand-md bg-dark navbar-dark">
<!-- Brand -->
<a class="navbar-brand" href="#">Customer Relationship Management</a>
<!-- Toggler/collapsibe Button -->
<button class="navbar-toggler" type="button" data-toggle="collapse" data- target="#collapsibleNavbar">
<span class="navbar-toggler-icon"></span> 
</button>

<!-- Navbar links -->
<div class="collapse navbar-collapse" id="collapsibleNavbar">
<ul class="navbar-nav"> 
<li class="nav-item">
<a class="nav-link" th:href="@{/customers}">Customer Relationship Management</a> 
</li>
</ul> 
</div>
</nav> 
<br> 
<br>

<div class = "container"> 
<div class = "row">
<div class ="col-lg-6 col-md-6 col-sm-6 container justify-content-center card">
<h1 class = "text-center"> Create New Customer </h1> 
<div class = "card-body">
<form th:action="@{/customers}" th:object = "${customer}" method="POST">

<div class ="form-group">
<label> Customer First Name </label> 
<input
type = "text"
name = "firstName"
th:field = "*{firstName}"
class = "form-control"
placeholder="Enter Customer First Name" 
  />
  </div>
  
<div class ="form-group">
<label> Customer Last Name </label> 
<input
type = "text"
name = "lastName"
th:field = "*{lastName}"
class = "form-control" 
placeholder="Enter Customer Last Name" 
/>
</div>

<div class ="form-group">
<label> Customer Email </label> 
<input
type = "text"
name = "email"
th:field = "*{email}"
  class = "form-control" 
    placeholder="Enter Customer Email" 
    />
    </div>
    
<div class = "box-footer">
<button type="submit" class = "btn btn-primary">
Submit
</button>
</div>
</form>
</div>
</div>
</div>
</div>
</body>
</html>

customers.html


<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"> 
<head>
<meta charset="ISO-8859-1">
<title>Customer Relationship Management </title> 
<link rel="stylesheet"
href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
integrity="sha384- Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
crossorigin="anonymous"> 
</head>

<body>
<nav class="navbar navbar-expand-md bg-dark navbar-dark">
  <!-- Brand -->
  <a class="navbar-brand text-center" href="#">Customer Relationship Management</a>
  
  <!-- Toggler/collapsibe Button -->
  <button class="navbar-toggler" type="button" data-toggle="collapse" data- target="#collapsibleNavbar">
  <span class="navbar-toggler-icon"></span> 
  </button>
  </nav>

  <div class ="container">
  <!-- <div class = "row">
       <h1> List Customer </h1>
       </div> -->
       <div><br><br></div> 
       <div class = "row">
      <div class = "col-lg-3">
      <a th:href = "@{/customers/new}" class = "btn btn-
      primary btn-sm mb-3"> Add Customer</a> 
      </div>
      </div>
      <table class = "table table-striped table-bordered">
      <thead class = "table-dark"> 
      <tr>
        <th> Customer First Name</th> 
        <th> Customer Last Name</th> 
        <th> Customer Email </th> 
        <th> Actions </th>
        </tr>
        </thead>
        
        <tbody>
        <tr th:each = "customer: ${customers}">
          <td th:text = "${customer.firstName}"></td> 
          <td th:text = "${customer.lastName}"></td> 
          <td th:text = "${customer.email}"></td> 
          <td>
          <a th:href = "@{/customers/edit/{id}(id=${customer.id})}"
            class = "btn btn-primary">Update</a> 
            <a th:href ="@{/customers/{id}(id=${customer.id})}"
              class = "btn btn-danger">Delete</a>
              </td>
              </tr>
              </tbody>
              </table>
              </div>
              </body>
              </html>
              
    edit_customer.html
    
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"> 
<head>
<meta charset="ISO-8859-1">
<title>Customer Relationship Management</title> 
<link rel="stylesheet"
href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
integrity="sha384- Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-md bg-dark navbar-dark">
  <!-- Brand -->
<a class="navbar-brand" href="#">Customer Relationship Management</a>

<!-- Toggler/collapsibe Button -->
<button class="navbar-toggler" type="button" data-toggle="collapse" data- target="#collapsibleNavbar">
<span class="navbar-toggler-icon"></span> 
</button>

<!-- Navbar links -->
<div class="collapse navbar-collapse" id="collapsibleNavbar"> 
<ul class="navbar-nav">
<li class="nav-item">
<a class="nav-link" th:href="@{/customers}">Customer Relationship Management</a>
</li>
</ul>
</div>
</nav>
<br>
<br>
<div class = "container">
<div class = "row">
<div class ="col-lg-6 col-md-6 col-sm-6 container justify- content-center card">

<h1 class = "text-center"> Update Customer </h1> 
<div class = "card-body">
<form th:action="@{/customers/{id} (id=${customer.id})}" th:object = "${customer}" method="POST">
   <div class ="form-group">
    <label> Customer First Name </label> 
    <input
      type = "text"
      name = "firstName"
      th:field = "*{firstName}"
      class = "form-control" placeholder="Enter Customer First Name"
      />
      </div>
      
      <div class ="form-group">
        <label> Customer Last Name </label> 
        <input
          type = "text"
          name = "lastName"
          th:field = "*{lastName}"
          class = "form-control" 
          placeholder="Enter Customer Last Name"
          />
          </div>
          
          <div class ="form-group">
            <label> Customer Email </label> 
            <input
            type = "text"
            name = "email"
            th:field = "*{email}"
            class = "form-control"
            placeholder="Enter Customer Email"
              />
              </div>
              <div class = "box-footer">
              <button type="submit" class = "btn btn-primary">
              Submit
              </button>
              </div>
              </form>
              </div>
              </div>
              </div>
              </div>
              </body>
              </html>
              
