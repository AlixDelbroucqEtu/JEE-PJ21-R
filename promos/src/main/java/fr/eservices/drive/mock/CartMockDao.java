package fr.eservices.drive.mock;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import fr.eservices.drive.dao.CartDao;
import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Cart;
import fr.eservices.drive.model.CartElement;

@Component
@Qualifier("mock")
public class CartMockDao implements CartDao {
	
	
	@Autowired
	ArticleMockDao articleDao;
	
	private HashMap<Integer, Cart> carts = new HashMap<>();
	
	public void setCart( int i, Cart cart) {
		carts.put(i, cart);
	}
	
	protected Cart cart_1() {
		Cart c = new Cart();
		
		c.getElements().add(new CartElement(articleDao.find("10101010"),1));
		c.getElements().add(new CartElement(articleDao.find("10101012"),1));
		c.getElements().add(new CartElement(articleDao.find("10101013"),1));
		
		return c;
	}
	
	@PostConstruct
	void initCarts() {
		carts.put(1, cart_1());
	}
	
	@Override
	public Cart getCartContent(int id) throws DataException {
		
		if ( id <= 0 ) throw new DataException("Invalid cart ID");
		
		return carts.get(id);
	}
	
	@Override
	public void store(int id, Cart cart) throws DataException {
		if ( id <= 0 ) throw new DataException("Invalid cart ID");
		if ( carts.get(id) != null ) throw new DataException("Cart already exists");
		carts.put(id, cart);
	}

}
