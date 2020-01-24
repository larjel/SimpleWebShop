package se.iths.jelleryd.webshop.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.jelleryd.webshop.entity.Category;
import se.iths.jelleryd.webshop.entity.Product;
import se.iths.jelleryd.webshop.web.model.ProductModel;

class UserServiceTest {

  private UserService userService;
  private Category categoryDVD;
  private Product product1;
  private ProductModel productModel1;

  @BeforeEach
  void setUp() throws Exception {
    userService = new UserService();
    categoryDVD = new Category("DVD");
    product1 = new Product("X23", "Movie", 123.0, categoryDVD, "Just a test movie");
    productModel1 = new ProductModel(product1);
  }

  @Test
  void testShoppingCartAddRemove() {
    userService.addToShoppingCart(productModel1);

    Optional<ProductModel> p = userService.getShoppingCart().getProduct("X23");

    assertTrue("Product not found", p.isPresent());

    userService.getShoppingCart().removeProduct("X23");

    p = userService.getShoppingCart().getProduct("X23");

    assertFalse("Product not removed", p.isPresent());
  }

}
