package se.iths.jelleryd.webshop.repository;

import org.springframework.data.repository.CrudRepository;
import se.iths.jelleryd.webshop.entity.OrderProduct;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
}
