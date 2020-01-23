package se.iths.jelleryd.webshop.web.model;

import se.iths.jelleryd.webshop.entity.Product;

public class ProductModel {
  Product product;
  Integer count;

  public ProductModel(Product product) {
    this.product = product;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

}
