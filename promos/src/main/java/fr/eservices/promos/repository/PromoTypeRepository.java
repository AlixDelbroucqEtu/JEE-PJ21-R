package fr.eservices.promos.repository;

import org.springframework.data.repository.CrudRepository;

import fr.eservices.promos.model.PromoType;

public interface PromoTypeRepository extends CrudRepository<PromoType, Integer> {}