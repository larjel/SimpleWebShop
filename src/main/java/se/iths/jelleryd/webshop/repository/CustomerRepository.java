package se.iths.jelleryd.webshop.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import se.iths.jelleryd.webshop.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
  Optional<Customer> findByUsername(String username);
}
