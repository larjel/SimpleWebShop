package se.iths.jelleryd.webshop;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.iths.jelleryd.webshop.entity.Category;
import se.iths.jelleryd.webshop.entity.Customer;
import se.iths.jelleryd.webshop.entity.CustomerOrder;
import se.iths.jelleryd.webshop.entity.OrderProduct;
import se.iths.jelleryd.webshop.entity.Product;
import se.iths.jelleryd.webshop.repository.CategoryRepository;
import se.iths.jelleryd.webshop.repository.CustomerRepository;
import se.iths.jelleryd.webshop.repository.OrderProductRepository;
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
  @Autowired
  private OrderProductRepository orderProductRepository;

  // @PostConstruct
  public void addMockDataSingle() {
    Category categoryDVD = new Category("BluRay");
    categoryRepository.save(categoryDVD);
  }

  // @PostConstruct
  public void addMockDataToDatabase() {

    Customer customer = new Customer("lars", "lars", "Lars", "J", "Hägersten", "12932", "Sweden");
    customerRepository.save(customer);

    Category categoryCD = new Category("CD");
    categoryRepository.save(categoryCD);

    Category categoryDVD = new Category("DVD");
    categoryRepository.save(categoryDVD);

    Category categoryBluRay = new Category("BluRay");
    categoryRepository.save(categoryBluRay);

    Product product1 =
        new Product("XC1234", "Terminator", 129.0, categoryDVD, "The first Terminator movie");
    productRepository.save(product1);

    Product product2 =
        new Product("XC4545", "Terminator 2", 145.0, categoryDVD, "The second Terminator movie");
    productRepository.save(product2);

    Product product3 = new Product("XC7845", "The Abyss", 114.0, categoryBluRay, "Under water");
    productRepository.save(product3);

    Product product4 = new Product("XC9395", "Accept", 90.0, categoryCD, "Restless and Wild");
    productRepository.save(product4);

    OrderProduct orderProduct1 = new OrderProduct(1, product1);
    orderProductRepository.save(orderProduct1);

    OrderProduct orderProduct2 = new OrderProduct(1, product2);
    orderProductRepository.save(orderProduct2);

    List<OrderProduct> myProducts = Arrays.asList(orderProduct1, orderProduct2);
    CustomerOrder order = new CustomerOrder(myProducts, customer);
    orderRepository.save(order);
  }

}
