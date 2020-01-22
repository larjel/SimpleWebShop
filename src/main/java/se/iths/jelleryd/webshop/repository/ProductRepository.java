package se.iths.jelleryd.webshop.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import se.iths.jelleryd.webshop.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
  List<Product> findByName(String name);

  // @Query("Select avg(result.score) " + "from Player as p join p.results as result where p.id =
  // :id")
  // Optional<Double> getResultAverage(int id);
}
