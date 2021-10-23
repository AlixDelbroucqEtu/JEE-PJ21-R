package fr.eservices.promos.repository;

import fr.eservices.promos.dao.CustomerDao;
import fr.eservices.promos.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
