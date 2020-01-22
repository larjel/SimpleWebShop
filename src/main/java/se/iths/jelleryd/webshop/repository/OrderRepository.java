package se.iths.jelleryd.webshop.repository;

import se.iths.jelleryd.webshop.entity.CustomerOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<CustomerOrder, Long> {
}
