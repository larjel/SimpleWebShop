package se.iths.jelleryd.webshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, unique = true)
  private String itemNumber;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Double price;

  @ManyToOne(fetch = FetchType.EAGER)
  private Category category;

  private String description;

  public Product() {
    // Required by JPA
  }

  public Product(String itemNumber, String name, Double price, Category category,
      String description) {
    this.itemNumber = itemNumber;
    this.name = name;
    this.price = price;
    this.category = category;
    this.description = description;
  }

  public String getItemNumber() {
    return itemNumber;
  }

  public void setItemNumber(String itemNumber) {
    this.itemNumber = itemNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getId() {
    return id;
  }

}
