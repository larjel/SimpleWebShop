package se.iths.jelleryd.webshop;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.iths.jelleryd.webshop.entity.Category;
import se.iths.jelleryd.webshop.entity.Customer;
import se.iths.jelleryd.webshop.entity.CustomerOrder;
import se.iths.jelleryd.webshop.entity.Product;
import se.iths.jelleryd.webshop.repository.CategoryRepository;
import se.iths.jelleryd.webshop.repository.CustomerRepository;
import se.iths.jelleryd.webshop.repository.OrderRepository;
import se.iths.jelleryd.webshop.repository.ProductRepository;

@Component
public class MockData {

  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private CategoryRepository categoryRepository;

  // @PostConstruct
  public void addMockDataToDatabase() {

    Customer customer = new Customer("lars", "lars", "Lars", "J", "Hägersten", "12932", "Sweden");
    customerRepository.save(customer);

    Category categoryCD = new Category("CD");
    categoryRepository.save(categoryCD);

    Category categoryDVD = new Category("DVD");
    categoryRepository.save(categoryDVD);

    Product product1 =
        new Product("XC1234", "Terminator", 129.0, categoryDVD, "The first Teminator movie");
    productRepository.save(product1);

    Product product2 =
        new Product("XC4545", "Terminator 2", 145.0, categoryDVD, "The second Teminator movie");
    productRepository.save(product2);

    List<Product> myProducts = Arrays.asList(product1, product2);
    CustomerOrder order = new CustomerOrder(myProducts, customer);
    orderRepository.save(order);
  }

}
