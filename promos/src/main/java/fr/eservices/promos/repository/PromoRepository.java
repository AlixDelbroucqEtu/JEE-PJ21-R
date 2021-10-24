package fr.eservices.promos.repository;

import org.springframework.data.repository.CrudRepository;

import fr.eservices.promos.model.Promo;

import java.util.List;

public interface PromoRepository extends CrudRepository<Promo, Integer> {

    Object findByType(String type);
}
