package fr.eservices.promos.repository;

import fr.eservices.promos.model.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
    List<Article> findByReferenceStartsWithIgnoreCaseOrLibelleStartsWithIgnoreCaseOrMarqueStartsWithIgnoreCase(String reference, String libelle, String marque);
}
