package se.iths.jelleryd.webshop.web;

import java.util.ArrayList;
import java.util.List;

public class CategoriesModel {

  List<String> categoriesList = new ArrayList<>();

  public CategoriesModel() {
    categoriesList.add("CD");
    categoriesList.add("DVD");
  }

  public List<String> getCategoriesList() {
    return categoriesList;
  }

  public void setCategoriesList(List<String> categories) {
    this.categoriesList = categories;
  }

}
