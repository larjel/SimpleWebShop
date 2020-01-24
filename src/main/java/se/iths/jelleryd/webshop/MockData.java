package se.iths.jelleryd.webshop;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.iths.jelleryd.webshop.entity.Category;
import se.iths.jelleryd.webshop.entity.Customer;
import se.iths.jelleryd.webshop.entity.Product;
import se.iths.jelleryd.webshop.repository.CategoryRepository;
import se.iths.jelleryd.webshop.repository.CustomerRepository;
import se.iths.jelleryd.webshop.repository.ProductRepository;

@Component
public class MockData {

  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private CategoryRepository categoryRepository;

  // @PostConstruct
  public void addMockDataSingle() {
    Category categoryDVD = new Category("BluRay");
    categoryRepository.save(categoryDVD);
  }

  // @PostConstruct
  public void addMockDataToDatabase() {

    Customer customer =
        new Customer("lars", "lars", "Lars", "Jelleryd", "Hägersten", "12932", "Sweden");
    customerRepository.save(customer);

    Category categoryCD = new Category("CD");
    categoryRepository.save(categoryCD);

    Category categoryVinyl = new Category("Vinyl");
    categoryRepository.save(categoryVinyl);

    Category categoryDVD = new Category("DVD");
    categoryRepository.save(categoryDVD);

    Category categoryBluRay = new Category("BluRay");
    categoryRepository.save(categoryBluRay);

    Product product =
        new Product("XC1234", "Terminator", 129.0, categoryDVD, "The first Terminator movie");
    productRepository.save(product);

    product =
        new Product("XC4545", "Terminator 2", 145.0, categoryDVD, "The second Terminator movie");
    productRepository.save(product);

    product = new Product("XC7845", "The Abyss", 114.0, categoryBluRay, "Under water");
    productRepository.save(product);

    product = new Product("XC9395", "Accept", 90.0, categoryCD, "Restless and Wild");
    productRepository.save(product);

    product = new Product("XC0456", "Iron Maiden", 118.0, categoryCD, "Piece of Mind");
    productRepository.save(product);

    product = new Product("XC7834", "Slayer", 108.0, categoryCD, "Reign in Blood");
    productRepository.save(product);

    product = new Product("XC6834", "Slayer", 170.0, categoryVinyl, "Reign in Blood");
    productRepository.save(product);

    product = new Product("XC6835", "Slayer", 108.0, categoryVinyl, "South of Heaven");
    productRepository.save(product);

    product =
        new Product("XC4964", "Terminator 2", 178.0, categoryBluRay, "The second Terminator movie");
    productRepository.save(product);

    product = new Product("XC9961", "Iron Maiden", 228.0, categoryBluRay, "Live in London");
    productRepository.save(product);
  }

}
