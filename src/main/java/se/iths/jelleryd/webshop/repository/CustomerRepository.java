package se.iths.jelleryd.webshop.repository;

import se.iths.jelleryd.webshop.entity.Customer;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
  List<Customer> findByUsername(String username);
}
