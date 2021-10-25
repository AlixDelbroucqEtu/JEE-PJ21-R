package fr.eservices.drive.web;

import java.io.ByteArrayOutputStream;

import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.eservices.drive.dao.ArticleDao;
import fr.eservices.drive.dao.CartDao;
import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.mock.ArticleMockDao;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Cart;
import fr.eservices.drive.model.CartElement;
import fr.eservices.drive.model.Order;
import fr.eservices.drive.repository.OrderRepository;
import fr.eservices.drive.web.dto.CartEntry;
import fr.eservices.drive.web.dto.SimpleResponse;
import fr.eservices.drive.web.dto.SimpleResponse.Status;

@Controller
@RequestMapping(path="/cart")
public class CartController {

	@Autowired
	CartDao daoCart;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ArticleDao daoArticle;

	@ExceptionHandler(DataException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String dataExceptionHandler(Exception ex) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintWriter w = new PrintWriter( out );
		ex.printStackTrace(w);
		w.close();
		return 
				"ERROR"
				+ "<!--\n" + out.toString() + "\n-->";
	}

	@GetMapping(path="/{id}.html", produces="text/html")
	public String getCart(@PathVariable(name="id") int id, Model model) throws DataException {
		if (id <=0) {
			throw new DataException("id inferieur à 0");
		}
		// get cart from dao
		Cart cart = daoCart.getCartContent(id);
		// assign to model var "cart"
		model.addAttribute("cart", cart);
		// return view name to display content of /WEB-INF/views/_cart_header.jsp
		return "_cart_header";
	}

	@ResponseBody
	@PostMapping(path="/{id}/add.json",consumes="application/json")
	public SimpleResponse add(@PathVariable(name="id") int id, @RequestBody CartEntry art) throws DataException {
		SimpleResponse res = new SimpleResponse();
		
		if (art.getQty() < 0) {
			res.status = Status.ERROR;
			res.message = "La quantité de l'article doit être positive";
			return res;
		}
		try {
			Article article= daoArticle.find(art.getId());
			if(article== null) {
				res.status = Status.ERROR;
				res.message = "Cet article n'existe pas";
				return res;
			}
			else {			
				Cart cart = daoCart.getCartContent(id);
				if (cart == null) {
					cart = new Cart();
					daoCart.store(id, cart);
				}
				
				List<CartElement> elements = cart.getElements();
				
				if(elements == null) elements = new ArrayList<CartElement>();
				
				boolean alreadyExist = false;
				for (CartElement element : elements) {
					if (element.getArticle().getId().equals(article.getId())) {
						alreadyExist = true;
						element.setQuantite(element.getQuantite()+art.getQty());
					}
				}
				if (!alreadyExist) elements.add(new CartElement(article, art.getQty()));
				
				System.out.println(
						"********************\n"
								+ "***** " + String.format("Add Article %d x [%s] to cart", art.getQty(), article.getId()) + "\n" 
								+ "********************"
						);
				
				
				
				res.status = Status.OK;
				
				return res;

			}
		} catch (DataException e) {
			throw new DataException("Cet article n'existe pas \n"+e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@PostMapping(path="/{id}/update.json",consumes="application/json")
	public SimpleResponse update(@PathVariable(name="id") int id, @RequestBody CartEntry art) throws DataException {

		SimpleResponse res = new SimpleResponse();
		
		Article article= daoArticle.find(art.getId());
		Cart cart = daoCart.getCartContent(id);
		if (cart==null) {
			res.status = Status.ERROR;
			res.message = "Cet article n'existe pas";
		} else {
		
			for (CartElement element : cart.getElements()) {
				if (element.getArticle().getId().equals(article.getId())) {
					element.setQuantite(art.getQty());
				}
			}
			
			System.out.println(
					"********************\n"
							+ "***** " + String.format("Update Article [%s] quantity %d in cart", article.getId(), art.getQty()) + "\n" 
							+ "********************"
					);
			
			res.status = Status.OK;
		}
		
		return res;
	}
	
	@ResponseBody
	@PostMapping(path="/{id}/remove.json",consumes="application/json")
	public SimpleResponse remove(@PathVariable(name="id") int id, @RequestBody String elementId) throws DataException {

		SimpleResponse res = new SimpleResponse();
		
		Cart cart = daoCart.getCartContent(id);
		if (cart==null) {
			res.status = Status.ERROR;
			res.message = "Ce panier n'existe pas";
		} else {
		
			//cart.getElements().remove(element);
			for (CartElement ce: cart.getElements()) {
				System.out.println("DELETE " + ce.getArticle().getId());
				System.out.println("DELETE " + elementId);
				System.out.println(ce.getArticle().getId().equals(elementId));
				if (ce.getArticle().getId().equals(elementId)) {
					cart.getElements().remove(ce);

				}
			}
			
			System.out.println(
					"********************\n"
							+ "***** " + String.format("Remove Article [%s] from cart", elementId) + "\n" 
							+ "********************"
					);
			
			for (CartElement ce: cart.getElements()) {
				System.out.println(ce.getArticle().getId());
			}
			
			res.status = Status.OK;
		}
		
		return res;
	}
	

	@RequestMapping("/{id}/validate.html")
	public String validateCart(@PathVariable(name="id") int id, Model model) throws DataException {

		// get cart by its id
		try {
			Cart cart = daoCart.getCartContent(id);
			if (cart == null) {
				throw new DataException("Cart is null");
			}
			// create an order
			Order order = new Order();
			

			// set order date
			order.setCreatedOn(new Date());
			order.setCurrentStatus(fr.eservices.drive.dao.Status.ORDERED);
			order.setCustomerId("chuckNorris");
			List<String>articles = new ArrayList<String>();
			int price =0;
			for(CartElement ce : cart.getElements()) {	
				articles.add(ce.getArticle().getId());
				price += ce.getArticle().getPrice();
			}
			order.setArticles(articles);
			// for each article, add it to the order

			// set order amount (sum of each articles' price)
			order.setAmount(price);
			orderRepository.save(order);
			// persist everything
			model.addAttribute("cart", cart);

			// redirect user to list of orders
			return "redirect:order/ofCustomer/chuckNorris.html";

		} catch (DataException e) {
			throw new DataException(e.getMessage());
		}
	}
}
