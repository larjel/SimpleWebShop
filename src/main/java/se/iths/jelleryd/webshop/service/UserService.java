package se.iths.jelleryd.webshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import se.iths.jelleryd.webshop.entity.Category;
import se.iths.jelleryd.webshop.entity.Customer;
import se.iths.jelleryd.webshop.entity.CustomerOrder;
import se.iths.jelleryd.webshop.entity.OrderLine;
import se.iths.jelleryd.webshop.entity.Product;
import se.iths.jelleryd.webshop.repository.CategoryRepository;
import se.iths.jelleryd.webshop.repository.CustomerRepository;
import se.iths.jelleryd.webshop.repository.OrderRepository;
import se.iths.jelleryd.webshop.repository.ProductRepository;
import se.iths.jelleryd.webshop.web.model.AddProductModel;
import se.iths.jelleryd.webshop.web.model.ProductModel;
import se.iths.jelleryd.webshop.web.model.ShoppingCartModel;

@Service
@SessionScope
public class UserService {

  private static final String ADMIN_USER_NAME = "admin";
  private static final String ADMIN_PASSWORD = "admin";

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
  private boolean isAdmin;
  private Customer customer;

  public boolean login(String username, String password) {
    isLoggedin = isAdmin = false;

    if (isAdminUserName(username)) {
      isLoggedin = isAdmin = username.equals(ADMIN_USER_NAME) && username.equals(ADMIN_PASSWORD);
    } else {
      Optional<Customer> customerOptional = customerRepository.findByUsername(username);
      if (customerOptional.isPresent()) {
        customer = customerOptional.get();
        if (customer.getPassword() != null && customer.getPassword().equals(password)) {
          isLoggedin = true;
        }
      }
    }
    return isLoggedin;
  }

  public boolean isAdminUserName(String username) {
    return username.trim().equalsIgnoreCase(ADMIN_USER_NAME);
  }

  public boolean isLoggedin() {
    return isLoggedin && customer != null;
  }

  public boolean isLoggedinAsAdmin() {
    return isLoggedin && isAdmin;
  }

  public void logout() {
    isLoggedin = isAdmin = false;
    customer = null;
  }

  public boolean addCustomer(Customer newCustomer) {
    Optional<Customer> customerOptional =
        customerRepository.findByUsername(newCustomer.getUsername());
    if (customerOptional.isPresent() || isAdminUserName(newCustomer.getUsername())) {
      return false; // User name already exists!
    }
    customerRepository.save(newCustomer);
    return true;
  }

  public List<Product> getProductsByCategory(String category) {
    Category categoryObj = getCategory(category);
    return productRepository.findByCategory(categoryObj);
  }

  public Optional<Product> getProductByItemNumber(String itemNumber) {
    return productRepository.findByItemNumber(itemNumber);
  }

  public Iterable<Category> getCategories() {
    return categoryRepository.findAll();
  }

  public Category getCategory(String name) {
    return categoryRepository.findByName(name);
  }

  public List<Product> getProductsByName(String name) {
    return productRepository.findByName(name);
  }

  public ShoppingCartModel getShoppingCart() {
    return shoppingCart;
  }

  public void addToShoppingCart(ProductModel product) {
    shoppingCart.addProduct(product);
  }

  public void removeFromShoppingCart(String itemNumber) {
    shoppingCart.removeProduct(itemNumber);
  }

  public void checkout() {
    Optional<Customer> cust = customerRepository.findByUsername(customer.getUsername());

    if (cust.isPresent()) {
      List<OrderLine> orders = new ArrayList<>();

      for (ProductModel model : shoppingCart.getProducts()) {
        orders.add(new OrderLine(model.getCount(), model.getProduct()));
      }

      CustomerOrder customerOrder = new CustomerOrder(orders, cust.get());

      orderRepository.save(customerOrder);
    }
  }

  @Async
  public void clearShoppingCart(long delayMs) {
    try {
      Thread.sleep(delayMs);
    } catch (InterruptedException e) {
    }
    shoppingCart.clear();
  }

  public Customer getCustomer() {
    return customer;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public Iterable<CustomerOrder> adminGetAllOrders() {
    return orderRepository.findAll();
  }

  public List<CustomerOrder> adminGetDispatchedOrders() {
    return orderRepository.findAllDispatchedOrders();
  }

  public List<CustomerOrder> adminGetNotDispatchedOrders() {
    return orderRepository.findAllNotDispatchedOrders();
  }

  public Optional<CustomerOrder> adminGetOrder(long orderNumber) {
    return orderRepository.findById(orderNumber);
  }

  public boolean adminUpdateOrderAsDispatched(long orderNumber) {
    Optional<CustomerOrder> order = adminGetOrder(orderNumber);
    if (order.isPresent()) {
      order.get().setDispatched(true);
      orderRepository.save(order.get());
      return true;
    }
    return false;
  }

  public void adminAddProduct(AddProductModel upm) {
    Category category = getCategory(upm.getCategory());

    Product newProduct = new Product(upm.getItemNumber(), upm.getName(), upm.getPrice(), category,
        upm.getDescription());

    productRepository.save(newProduct);
  }

}
