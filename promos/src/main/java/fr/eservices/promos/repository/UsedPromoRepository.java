package fr.eservices.promos.repository;


import fr.eservices.promos.model.UsedPromo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsedPromoRepository extends CrudRepository<UsedPromo, Integer> {

    public List<UsedPromo> findAllByCustomer_Id(Integer id);
}
