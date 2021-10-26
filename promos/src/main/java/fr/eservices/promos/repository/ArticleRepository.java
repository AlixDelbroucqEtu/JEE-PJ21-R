package fr.eservices.promos.repository;

import fr.eservices.promos.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
    List<Article> findByReferenceStartsWithIgnoreCaseOrLibelleStartsWithIgnoreCaseOrMarqueStartsWithIgnoreCase(String reference, String libelle, String marque);

    @Query(value="select * from Article where cat_id = :categoryId"
            + " and (UPPER(reference) LIKE UPPER(CONCAT(:substring,'%')) OR UPPER(libelle) LIKE UPPER(CONCAT(:substring,'%')) OR UPPER(marque) LIKE UPPER(CONCAT(:substring,'%')))", nativeQuery = true)
    List<Article> findWhereCategory(@Param("substring") String substring, @Param("categoryId") int categoryId);
}
