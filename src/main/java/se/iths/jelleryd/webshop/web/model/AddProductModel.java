package se.iths.jelleryd.webshop.web.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import se.iths.jelleryd.webshop.entity.Category;

public class AddProductModel {
  private String itemNumber;

  private String name;

  private Double price;

  private String category;

  private String description;

  private Map<String, String> categories = new TreeMap<>();

  public AddProductModel() {}

  public AddProductModel(String itemNumber, String name, Double price, String category,
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

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setCategoriesFromIterable(Iterable<Category> cat) {
    for (Category c : cat) {
      categories.put(c.getName(), c.getName());
    }
  }

  public void setCategoriesFromList(List<String> cat) {
    for (String c : cat) {
      categories.put(c, c);
    }
  }

  public Map<String, String> getCategories() {
    return categories;
  }

}
