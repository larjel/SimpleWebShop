package se.iths.jelleryd.webshop.web;

import java.util.Arrays;
import java.util.List;
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
import se.iths.jelleryd.webshop.entity.CustomerOrder;
import se.iths.jelleryd.webshop.entity.Product;
import se.iths.jelleryd.webshop.service.UserService;
import se.iths.jelleryd.webshop.web.model.AddCustomerModel;
import se.iths.jelleryd.webshop.web.model.AddProductModel;
import se.iths.jelleryd.webshop.web.model.AdminOrderModel;
import se.iths.jelleryd.webshop.web.model.CategoriesModel;
import se.iths.jelleryd.webshop.web.model.LoginFormModel;
import se.iths.jelleryd.webshop.web.model.ProductModel;
import se.iths.jelleryd.webshop.web.model.ProductsModel;
import se.iths.jelleryd.webshop.web.model.SearchModel;

@Controller
public class MainController {

  @Autowired
  UserService customerService;

  @GetMapping("/")
  public String loginForm(Model model) {
    model.addAttribute("loginFormModel", new LoginFormModel());
    model.addAttribute("message", "Please login:");
    return "customerLogin";
  }

  @PostMapping("/")
  public String loginSubmit(@ModelAttribute LoginFormModel loginFormModel, Model model) {

    if (customerService.login(loginFormModel.getUserName(), loginFormModel.getPassword())) {
      if (customerService.isAdmin()) {
        return adminHomePage(model);
      } else {
        return categoriesForm(model);
      }
    } else {
      model.addAttribute("message", "No such user or bad password, please try again");
      return "customerLogin";
    }
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


  @GetMapping("/categories")
  public String categoriesForm(Model model) {
    if (!customerService.isLoggedin())
      return loginForm(model);

    model.addAttribute("searchModel", new SearchModel());
    model.addAttribute("categoriesModel", new CategoriesModel(customerService.getCategories()));
    return "categories";
  }

  @GetMapping("/shoppingCart")
  public String shoppingCart(Model model) {
    if (!customerService.isLoggedin())
      return loginForm(model);

    model.addAttribute("cart", customerService.getShoppingCart());
    return "shoppingCart";
  }

  @PostMapping("/shoppingCart")
  public String addToShoppingCart(@ModelAttribute ProductModel productModel, Model model) {
    if (!customerService.isLoggedin())
      return loginForm(model);

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
    if (!customerService.isLoggedin())
      return loginForm(model);

    model.addAttribute("category", category);
    model.addAttribute("productsModel",
        new ProductsModel(customerService.getProductsByCategory(category)));
    return "products";
  }

  @GetMapping("/removeFromCart")
  public String removeFromCart(
      @RequestParam(value = "itemNumber", required = true) String itemNumber, Model model) {
    if (!customerService.isLoggedin())
      return loginForm(model);

    customerService.removeFromShoppingCart(itemNumber);
    return shoppingCart(model);
  }

  @GetMapping("/updateProductInCart")
  public String updateProductInCart(
      @RequestParam(value = "itemNumber", required = true) String itemNumber, Model model) {
    if (!customerService.isLoggedin())
      return loginForm(model);

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
    if (!customerService.isLoggedin())
      return loginForm(model);

    customerService.getShoppingCart().updateProductQuantity(productModel.getItemNumber(),
        productModel.getCount());

    return shoppingCart(model);
  }

  @GetMapping("/product")
  public String product(@RequestParam(value = "itemNumber", required = true) String itemNumber,
      Model model) {
    if (!customerService.isLoggedin())
      return loginForm(model);

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
    if (!customerService.isLoggedin())
      return loginForm(model);

    customerService.checkout();

    model.addAttribute("cart", customerService.getShoppingCart());

    // Clear cart asynchronously after rendering checkout page
    customerService.clearShoppingCart(2000);

    return "checkout";
  }

  @GetMapping("/profile")
  public String viewProfile(Model model) {
    if (!customerService.isLoggedin())
      return loginForm(model);

    model.addAttribute("customer", customerService.getCustomer());
    return "profile";
  }

  @PostMapping("/search")
  public String search(@ModelAttribute SearchModel searchModel, Model model) {
    if (!customerService.isLoggedin())
      return loginForm(model);

    String partialWildcardSearch = "%" + searchModel.getProductName() + "%";

    List<Product> products = customerService.getProductsByName(partialWildcardSearch);

    model.addAttribute("category", "Search results");
    model.addAttribute("productsModel", new ProductsModel(products));

    return "products";
  }

  @GetMapping("/adminHome")
  public String adminHomePage(Model model) {
    if (!customerService.isLoggedinAsAdmin())
      return loginForm(model);

    return "adminHome";
  }

  @GetMapping("/adminOrders")
  public String adminOrdersPage(@RequestParam(value = "show", required = true) String show,
      Model model) {
    if (!customerService.isLoggedinAsAdmin())
      return loginForm(model);

    String orderType;
    AdminOrderModel orderModel;

    if (show.equals("notdispatched")) {
      orderModel = new AdminOrderModel(customerService.adminGetNotDispatchedOrders());
      orderType = "Not Dispatched Orders";
    } else if (show.equals("dispatched")) {
      orderModel = new AdminOrderModel(customerService.adminGetDispatchedOrders());
      orderType = "Dispatched Orders";
    } else { // All orders
      orderModel = new AdminOrderModel(customerService.adminGetAllOrders());
      orderType = "All Orders";
    }

    model.addAttribute("orderType", orderType);
    model.addAttribute("adminOrderModel", orderModel);
    return "adminOrders";
  }

  @GetMapping("/adminOrderDetails")
  public String adminOrderDetails(
      @RequestParam(value = "orderNumber", required = true) Long orderNumber, Model model) {
    if (!customerService.isLoggedinAsAdmin())
      return loginForm(model);

    Optional<CustomerOrder> order = customerService.adminGetOrder(orderNumber);
    if (order.isPresent()) {
      model.addAttribute("orderType", "Customer Order #" + order.get().getOrderNumber());
      model.addAttribute("customerOrder", order.get());
      return "adminOrderDetails";
    }
    return "error"; // Should not happen
  }

  @GetMapping("/adminDispatched")
  public String adminDispatchedOrder(
      @RequestParam(value = "orderNumber", required = true) Long orderNumber, Model model) {
    if (!customerService.isLoggedinAsAdmin())
      return loginForm(model);

    if (customerService.adminUpdateOrderAsDispatched(orderNumber)) {
      return adminOrdersPage("dispatched", model);
    }
    return "error"; // Should not happen
  }

  @GetMapping("/adminAddProduct")
  public String adminAddProduct(Model model) {
    if (!customerService.isLoggedinAsAdmin())
      return loginForm(model);

    AddProductModel addProductModel = new AddProductModel();
    addProductModel.setCategoriesFromList(Arrays.asList("DVD", "Vinyl"));
    model.addAttribute("addProductModel", addProductModel);
    return "adminAddProduct";
  }

  @GetMapping("/logout")
  public String logout(Model model) {
    customerService.logout();
    return loginForm(model);
  }

}
