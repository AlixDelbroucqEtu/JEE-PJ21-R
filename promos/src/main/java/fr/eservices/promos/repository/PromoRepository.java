package fr.eservices.promos.repository;

import org.springframework.data.repository.CrudRepository;

import fr.eservices.promos.model.Promo;

public interface PromoRepository extends CrudRepository<Promo, Integer> {}
