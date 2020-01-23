package se.iths.jelleryd.webshop.web.model;

import se.iths.jelleryd.webshop.entity.Category;

public class CategoriesModel {

  Iterable<Category> categoriesList;

  public CategoriesModel(Iterable<Category> categoriesList) {
    this.categoriesList = categoriesList;
  }

  public Iterable<Category> getCategoriesList() {
    return categoriesList;
  }

  public void setCategoriesList(Iterable<Category> categories) {
    this.categoriesList = categories;
  }

}
