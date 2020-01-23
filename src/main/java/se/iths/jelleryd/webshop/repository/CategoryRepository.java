package se.iths.jelleryd.webshop.repository;

import org.springframework.data.repository.CrudRepository;
import se.iths.jelleryd.webshop.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
  Category findByName(String name);
}
