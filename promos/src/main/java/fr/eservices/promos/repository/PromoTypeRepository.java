package fr.eservices.promos.repository;

import org.springframework.data.repository.CrudRepository;

import fr.eservices.promos.model.PromoType;

import java.util.List;

public interface PromoTypeRepository extends CrudRepository<PromoType, Integer> {
    public List<PromoType> findAllBy();
}