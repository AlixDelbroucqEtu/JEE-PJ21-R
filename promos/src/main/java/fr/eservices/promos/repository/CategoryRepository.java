package fr.eservices.promos.repository;

import fr.eservices.promos.model.Article;
import fr.eservices.promos.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
