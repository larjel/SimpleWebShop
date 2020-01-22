package se.iths.jelleryd.webshop.web;

import java.util.ArrayList;
import java.util.List;
import se.iths.jelleryd.webshop.entity.Product;

public class ShoppingCartModel {

  private List<Product> products = new ArrayList<>();

  public void addProduct(Product product) {
    products.add(product);
  }

  public List<Product> getProducts() {
    return products;
  }

  public void clear() {
    products.clear();
  }

}
