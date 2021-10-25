package fr.eservices.promos.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.eservices.promos.model.Promo;

import java.util.List;

public interface PromoRepository extends CrudRepository<Promo, Integer> {
    @Query(value = "select p from Promo p where p.promoType.type = 'OFFRE MARKETING'")
    List<Promo> findAllMarketingCampains();

    List<Promo> findAll();
}
