package se.iths.jelleryd.webshop.web;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.jelleryd.webshop.entity.Product;
import se.iths.jelleryd.webshop.service.CustomerServiceImpl;
import se.iths.jelleryd.webshop.web.model.AddCustomerModel;
import se.iths.jelleryd.webshop.web.model.CategoriesModel;
import se.iths.jelleryd.webshop.web.model.LoginFormModel;
import se.iths.jelleryd.webshop.web.model.ProductModel;
import se.iths.jelleryd.webshop.web.model.ProductsModel;
import se.iths.jelleryd.webshop.web.model.SearchModel;

@Controller
public class MainController {

  @Autowired
  // TODO: Change to use interface
  CustomerServiceImpl customerService;

  @GetMapping("/")
  public String loginForm(Model model) {
    model.addAttribute("loginFormModel", new LoginFormModel());
    model.addAttribute("message", "Please login:");
    return "customerLogin";
  }

  @PostMapping("/")
  public String loginSubmit(@ModelAttribute LoginFormModel loginFormModel, Model model) {

    if (customerService.login(loginFormModel.getUserName(), loginFormModel.getPassword())) {
      return categoriesForm(model);
    } else {
      model.addAttribute("message", "No such user or bad password, please try again");
      return "customerLogin";
    }
  }

  @GetMapping("/categories")
  public String categoriesForm(Model model) {
    model.addAttribute("searchModel", new SearchModel());
    model.addAttribute("categoriesModel", new CategoriesModel(customerService.getCategories()));
    return "categories";
  }

  @GetMapping("/shoppingCart")
  public String shoppingCart(Model model) {
    model.addAttribute("cart", customerService.getShoppingCart());
    return "shoppingCart";
  }

  @PostMapping("/shoppingCart")
  public String addToShoppingCart(@ModelAttribute ProductModel productModel, Model model) {
    Optional<Product> product =
        customerService.getProductByItemNumber(productModel.getItemNumber());
    if (product.isPresent()) {
      productModel.setProduct(product.get());
      customerService.addToShoppingCart(productModel);
      return shoppingCart(model);
    }
    return "error"; // Should not happen
  }

  @GetMapping("/products")
  public String productList(@RequestParam(value = "category", required = true) String category,
      Model model) {
    model.addAttribute("category", category);
    model.addAttribute("productsModel",
        new ProductsModel(customerService.getProductsByCategory(category)));
    return "products";
  }

  @GetMapping("/removeFromCart")
  public String removeFromCart(
      @RequestParam(value = "itemNumber", required = true) String itemNumber, Model model) {
    customerService.removeFromShoppingCart(itemNumber);
    return shoppingCart(model);
  }

  @GetMapping("/updateProductInCart")
  public String updateProductInCart(
      @RequestParam(value = "itemNumber", required = true) String itemNumber, Model model) {
    Optional<ProductModel> product = customerService.getShoppingCart().getProduct(itemNumber);
    if (product.isPresent()) {
      model.addAttribute("postLink", "/updateProductInCart");
      model.addAttribute("buttonText", "Update cart");
      model.addAttribute("productModel", product.get());
      return "product";
    }
    return "error"; // Should not happen
  }

  @PostMapping("/updateProductInCart")
  public String updateProductInCartFormPost(@ModelAttribute ProductModel productModel,
      Model model) {

    customerService.getShoppingCart().updateProductQuantity(productModel.getItemNumber(),
        productModel.getCount());

    return shoppingCart(model);
  }

  @GetMapping("/product")
  public String product(@RequestParam(value = "itemNumber", required = true) String itemNumber,
      Model model) {
    Optional<Product> product = customerService.getProductByItemNumber(itemNumber);
    if (product.isPresent()) {
      model.addAttribute("postLink", "/shoppingCart");
      model.addAttribute("buttonText", "Add to cart");
      model.addAttribute("productModel", new ProductModel(product.get()));
      return "product";
    }
    return "products";
  }

  @GetMapping("/checkout")
  public String checkout(Model model) {
    customerService.checkout();

    model.addAttribute("cart", customerService.getShoppingCart());

    // Clear cart asynchronously after rendering checkout page
    customerService.clearShoppingCart(2000);

    return "checkout";
  }

  @GetMapping("/addCustomer")
  public String addCustomer(Model model) {
    model.addAttribute("addCustomerModel", new AddCustomerModel());
    return "addCustomer";
  }

  @PostMapping("/addCustomer")
  public String saveCustomer(@Valid AddCustomerModel addUserModel, BindingResult bindingResult) {

    if (!addUserModel.getPassword().equals(addUserModel.getRepeatPassword())) {
      bindingResult.rejectValue("repeatPassword", "PasswordsDontMatch");
    } else if (!customerService.addCustomer(addUserModel.modelToCustomer())) {
      bindingResult.rejectValue("username", "UserNameAlreadyTaken");
    }

    if (bindingResult.hasErrors()) {
      return "addCustomer";
    }

    return "customerAdded";
  }

}
