package se.iths.jelleryd.webshop.web.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoppingCartModel {

  List<ProductModel> products = new ArrayList<>();

  public void addProduct(ProductModel product) {
    if (!isProductInCart(product.getItemNumber())) {
      products.add(product);
    }
  }

  public boolean removeProduct(String itemNumber) {
    Optional<ProductModel> product = getProduct(itemNumber);
    if (product.isPresent()) {
      products.remove(product.get());
      return true;
    }
    return false;
  }

  public boolean isProductInCart(String itemNumber) {
    Optional<ProductModel> product = getProduct(itemNumber);
    return product.isPresent();
  }

  public Optional<ProductModel> getProduct(String itemNumber) {
    for (ProductModel product : products) {
      if (product.getProduct().getItemNumber().equals(itemNumber)) {
        return Optional.of(product);
      }
    }
    return Optional.empty();
  }

  public boolean updateProductQuantity(String itemNumber, int quantity) {
    Optional<ProductModel> product = getProduct(itemNumber);
    if (product.isPresent()) {
      product.get().setCount(quantity);
      return true;
    }
    return false;
  }

  public List<ProductModel> getProducts() {
    return products;
  }

  public void clear() {
    products.clear();
  }

  public double sumOfAllProducts() {
    double sum = 0.0;
    for (ProductModel product : products) {
      sum += product.getProduct().getPrice() * product.getCount();
    }
    return sum;
  }

  @Override
  public String toString() {
    return "ShoppingCartModel [products=" + products + "]";
  }

}
