package se.iths.jelleryd.webshop.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import se.iths.jelleryd.webshop.entity.Category;
import se.iths.jelleryd.webshop.entity.Customer;
import se.iths.jelleryd.webshop.entity.Product;
import se.iths.jelleryd.webshop.repository.CategoryRepository;
import se.iths.jelleryd.webshop.repository.CustomerRepository;
import se.iths.jelleryd.webshop.repository.OrderRepository;
import se.iths.jelleryd.webshop.repository.ProductRepository;
import se.iths.jelleryd.webshop.web.model.ShoppingCartModel;

@Service
@SessionScope
public class CustomerServiceImpl {

  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private CategoryRepository categoryRepository;

  private ShoppingCartModel shoppingCart = new ShoppingCartModel();

  private boolean isLoggedin;
  private Customer customer;

  public boolean login(String username, String password) {
    isLoggedin = false;
    Optional<Customer> customerOptional = customerRepository.findByUsername(username);
    if (customerOptional.isPresent()) {
      customer = customerOptional.get();
      if (customer.getPassword() != null && customer.getPassword().equals(password)) {
        isLoggedin = true;
      }
    }
    return isLoggedin;
  }

  public boolean addCustomer(Customer newCustomer) {
    Optional<Customer> customerOptional =
        customerRepository.findByUsername(newCustomer.getUsername());
    if (customerOptional.isPresent()) {
      return false; // User name already exists!
    }
    customerRepository.save(newCustomer);
    return true;
  }

  public List<Product> getProductsByCategory(String category) {
    Category categoryObj = categoryRepository.findByName(category);
    return productRepository.findByCategory(categoryObj);
  }

  public Optional<Product> getProductByItemNumber(String itemNumber) {
    return productRepository.findByItemNumber(itemNumber);
  }

  public Iterable<Category> getCategories() {
    return categoryRepository.findAll();
  }

  public ShoppingCartModel getShoppingCart() {
    return shoppingCart;
  }

}
