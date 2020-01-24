package se.iths.jelleryd.webshop.repository;

import org.springframework.data.repository.CrudRepository;
import se.iths.jelleryd.webshop.entity.OrderLine;

public interface OrderLineRepository extends CrudRepository<OrderLine, Long> {
}
