package se.iths.jelleryd.webshop.rest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.iths.jelleryd.webshop.entity.CustomerOrder;
import se.iths.jelleryd.webshop.service.UserService;
import se.iths.jelleryd.webshop.web.model.AddProductModel;

@RestController
public class RestApiController {

  @Autowired
  UserService userService;

  @GetMapping("/admin/allorders")
  public ResponseEntity<List<CustomerOrder>> allOrders() {
    List<CustomerOrder> orders = iterableToFilteredList(userService.adminGetAllOrders());

    if (orders != null) {
      return ResponseEntity.ok().body(orders);
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/admin/dispatchedorders")
  public ResponseEntity<List<CustomerOrder>> dispatchedOrders() {
    List<CustomerOrder> orders = iterableToFilteredList(userService.adminGetDispatchedOrders());

    if (orders != null) {
      return ResponseEntity.ok().body(orders);
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/admin/notdispatchedorders")
  public ResponseEntity<List<CustomerOrder>> adminGetNotDispatchedOrders() {
    List<CustomerOrder> orders = iterableToFilteredList(userService.adminGetNotDispatchedOrders());

    if (orders != null) {
      return ResponseEntity.ok().body(orders);
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/admin/setdispatched/{ordernumber}")
  public ResponseEntity<String> setDispatched(@PathVariable("ordernumber") Long ordernumber) {

    if (userService.adminUpdateOrderAsDispatched(ordernumber)) {
      return ResponseEntity.accepted().body("Order marked as dispatched!");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error! Order not found.");
    }
  }

  @GetMapping("/admin/addproduct/{itemnumber}/{name}/{price}/{description}/{category}")
  public ResponseEntity<String> addProduct(@PathVariable("itemnumber") String itemnumber,
      @PathVariable("name") String name, @PathVariable("price") Double price,
      @PathVariable("description") String description, @PathVariable("category") String category) {
    try {
      AddProductModel addProductModel =
          new AddProductModel(itemnumber, name, price, category, description);
      userService.adminAddProduct(addProductModel);

      return ResponseEntity.accepted().body("Product added!");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error! Bad data input?");
    }
  }

  private List<CustomerOrder> iterableToFilteredList(Iterable<CustomerOrder> orders) {
    if (orders != null) {
      List<CustomerOrder> orderList = new ArrayList<>();
      for (CustomerOrder o : orders) {
        // Including Customer will cause recursion in JSON parser. May need to look at this later.
        o.setCustomer(null);
        orderList.add(o);
      }
      return orderList;
    }
    return null;
  }

}
