package fr.eservices.promos.repository;


import fr.eservices.promos.model.UsedPromo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsedPromoRepository extends JpaRepository<UsedPromo, Integer> {
}
