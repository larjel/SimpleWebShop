package se.iths.jelleryd.webshop.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import se.iths.jelleryd.webshop.entity.Customer;
import se.iths.jelleryd.webshop.repository.CategoryRepository;
import se.iths.jelleryd.webshop.repository.CustomerRepository;
import se.iths.jelleryd.webshop.repository.OrderRepository;
import se.iths.jelleryd.webshop.repository.ProductRepository;
import se.iths.jelleryd.webshop.web.ShoppingCartModel;

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
    List<Customer> pList = customerRepository.findByUsername(username);
    if (!pList.isEmpty()) {
      customer = pList.get(0);
      if (customer.getPassword() != null && customer.getPassword().equals(password))
        isLoggedin = true;
    }
    return isLoggedin;
  }

  public boolean addCustomer(String username) {
    List<Customer> pList = customerRepository.findByUsername(username);
    if (pList.isEmpty()) {
      // TODO: Add all fields!
      // customerRepository.save(new Customer(username));
      customerRepository.save(new Customer());
      return true;
    }
    return false;
  }

  public ShoppingCartModel getShoppingCart() {
    return shoppingCart;
  }

}
