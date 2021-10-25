package fr.eservices.promos.repository;

import fr.eservices.promos.model.Cart;
import fr.eservices.promos.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Integer> {

    public Cart findByCustomerId(int customerId);
}
