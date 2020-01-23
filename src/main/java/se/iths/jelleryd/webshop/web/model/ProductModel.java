package se.iths.jelleryd.webshop.web.model;

import se.iths.jelleryd.webshop.entity.Product;

public class ProductModel {
  private Product product;
  private Integer count;
  private String itemNumber;

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

  public String getItemNumber() {
    return itemNumber;
  }

  public void setItemNumber(String itemNumber) {
    this.itemNumber = itemNumber;
  }

  @Override
  public String toString() {
    return "ProductModel [product=" + product + ", count=" + count + ", itemNumber=" + itemNumber
        + "]";
  }

}
