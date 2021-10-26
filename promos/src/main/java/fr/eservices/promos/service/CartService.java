package fr.eservices.promos.service;

import fr.eservices.promos.model.Cart;
import fr.eservices.promos.model.CartElement;
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

    public boolean isInCart(int customerId, int articeId) {
        Cart cart = findByCustomerId(customerId);
        if (cart==null) return false;
        for (CartElement ce: cart.getElements()) {
            if (ce.getArticle().getId()==articeId) {
                return true;
            }
        }
        return false;
    }
}
