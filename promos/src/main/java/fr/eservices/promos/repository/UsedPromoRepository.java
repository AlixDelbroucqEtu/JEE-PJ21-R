package fr.eservices.promos.repository;

import fr.eservices.promos.model.Promo;
import fr.eservices.promos.model.UsedPromo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsedPromoRepository extends CrudRepository<UsedPromo, Integer> {
    @Query( "select u.promo from UsedPromo u where u.customer.id = :id" )
    public List<Promo> findByCustomer(Integer id);
}
