package se.iths.jelleryd.webshop.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class CustomerOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long orderNumber;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<OrderLine> products;

  @ManyToOne // Bi-directional relationship
  private Customer customer;

  private boolean dispatched;

  public CustomerOrder() {
    // Required by JPA
  }

  public CustomerOrder(List<OrderLine> products, Customer customer) {
    this.products = products;
    this.customer = customer;
  }

  public List<OrderLine> getProducts() {
    return products;
  }

  public void setProducts(List<OrderLine> products) {
    this.products = products;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Long getOrderNumber() {
    return orderNumber;
  }

  public boolean isDispatched() {
    return dispatched;
  }

  public void setDispatched(boolean dispatched) {
    this.dispatched = dispatched;
  }

  public double sumOfAllProducts() {
    double sum = 0.0;
    for (OrderLine product : products) {
      sum += product.getProduct().getPrice() * product.getQuantity();
    }
    return sum;
  }

}
