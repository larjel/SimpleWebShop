package se.iths.jelleryd.webshop.web;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import se.iths.jelleryd.webshop.service.CustomerServiceImpl;

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

      // model.addAttribute("userName", loginFormModel.getUserName());
      // model.addAttribute("password", loginFormModel.getPassword());
      model.addAttribute("searchModel", new SearchModel());
      model.addAttribute("categoriesModel", new CategoriesModel());

      return "categories";
    } else {
      model.addAttribute("message", "No such user or bad password, please try again");
      return "customerLogin";
    }
  }

  @GetMapping("/products")
  public String productList(@RequestParam(value = "category", required = true) String category,
      Model model) {
    // model.addAttribute("loginFormModel", new LoginFormModel());
    // model.addAttribute("message", "Please login:");
    return "products";
  }


  @GetMapping("/addUser")
  public ModelAndView addUser() {
    return new ModelAndView("newUser", "addUserModel", new AddUserModel());
  }

  @PostMapping("/saveUser")
  public String saveUser(@Valid AddUserModel addUserModel, BindingResult bindingResult) {

    new AddUserModelValidator().validate(addUserModel, bindingResult);

    if (bindingResult.hasErrors()) {
      return "newUser";
    }
    return "userAdded";
  }

}
