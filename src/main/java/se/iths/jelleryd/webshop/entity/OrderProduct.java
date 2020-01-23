package se.iths.jelleryd.webshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OrderProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private Integer quantity;

  @OneToOne(fetch = FetchType.EAGER)
  private Product product;

  public OrderProduct() {
    // Required by JPA
  }

  public OrderProduct(Integer quantity, Product product) {
    this.quantity = quantity;
    this.product = product;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Long getId() {
    return id;
  }

}
