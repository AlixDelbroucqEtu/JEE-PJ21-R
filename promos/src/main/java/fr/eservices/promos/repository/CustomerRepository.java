package fr.eservices.promos.repository;


import fr.eservices.promos.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
