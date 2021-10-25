package fr.eservices.promos.repository;


import fr.eservices.promos.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    public List<Customer> findAll();

    public Customer findById(Long id);
}
