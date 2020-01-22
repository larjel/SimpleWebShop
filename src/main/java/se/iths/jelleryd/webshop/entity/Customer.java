package se.iths.jelleryd.webshop.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String secondName;

  @Column(nullable = false)
  private String address1;
  private String address2;
  private String address3;

  @Column(nullable = false)
  private String zip;

  @Column(nullable = false)
  private String country;

  // Bi-directional relationship
  @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<CustomerOrder> orders;

  public Customer() {
    // Required by JPA
  }

  public Customer(String username, String password, String firstName, String secondName,
      String address1, String zip, String country) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.secondName = secondName;
    this.address1 = address1;
    this.zip = zip;
    this.country = country;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSecondName() {
    return secondName;
  }

  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getAddress3() {
    return address3;
  }

  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public List<CustomerOrder> getOrders() {
    return orders;
  }

  public void setOrders(List<CustomerOrder> orders) {
    this.orders = orders;
  }

  public void addOrder(CustomerOrder order) {
    if (orders == null) {
      orders = new ArrayList<>();
    }
    orders.add(order);
  }

  public Long getId() {
    return id;
  }

}
