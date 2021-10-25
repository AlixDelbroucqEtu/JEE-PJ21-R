package fr.eservices.promos.service;

import fr.eservices.promos.model.Cart;
import fr.eservices.promos.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    public Cart findByCustomerId(int customerId) {
       return cartRepository.findByCustomerId(customerId);
    }

    public void save(Cart cart) {
        cartRepository.save(cart);
    }
}
