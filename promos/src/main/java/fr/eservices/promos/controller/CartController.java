package fr.eservices.promos.controller;

import java.io.ByteArrayOutputStream;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


import fr.eservices.promos.dto.CartEntry;
import fr.eservices.promos.dto.SimpleResponse;
import fr.eservices.promos.model.Article;
import fr.eservices.promos.model.CartElement;
import fr.eservices.promos.model.Customer;
import fr.eservices.promos.model.Promo;
import fr.eservices.promos.model.UsedPromo;
import fr.eservices.promos.repository.OrderRepository;
import fr.eservices.promos.service.ArticleService;
import fr.eservices.promos.service.CartService;
import fr.eservices.promos.service.CustomerService;
import fr.eservices.promos.service.PromoService;
import fr.eservices.promos.service.Used_PromoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import fr.eservices.promos.exception.DataException;
import fr.eservices.promos.model.Cart;

@Controller
@RequestMapping(path="/cart")
public class CartController {

	@Autowired
	CartService cartService;

	@Autowired
	ArticleService articleService;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	PromoService promoService;

	@Autowired
	CustomerService customerService;

	@Autowired
	Used_PromoService used_PromoService;


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
		Cart cart = cartService.findByCustomerId(id);
		if (cart==null) {
			cart = new Cart(id);
			cartService.save(cart);
		}
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
			res.status = SimpleResponse.Status.ERROR;
			res.message = "La quantité de l'article doit être positive";
			return res;
		}

		Article article= articleService.findById(art.getId());
		if(article== null) {
			res.status = SimpleResponse.Status.ERROR;
			res.message = "Cet article n'existe pas";
			return res;
		}
		else {
			Cart cart = cartService.findByCustomerId(id);
			if (cart == null) {
				cart = new Cart(id);
				cartService.save(cart);
			}

			List<CartElement> elements = cart.getElements();

			if (elements == null) elements = new ArrayList<CartElement>();

			CartElement currentElement = null;
			for (CartElement element : elements) {
				if (element.getArticle().getId() == article.getId()) {
					element.setQuantite(element.getQuantite() + art.getQty());
					currentElement = element;
				}
			}

			if (currentElement == null) {
				currentElement = new CartElement(article, art.getQty());
				elements.add(currentElement);
			}

			cartService.save(cart);

			res.status = SimpleResponse.Status.OK;

			return res;

		}

	}


	@ResponseBody
	@PostMapping(path="/{id}/update.json",consumes="application/json")
	public SimpleResponse update(@PathVariable(name="id") int id, @RequestBody CartEntry art) throws DataException {

		SimpleResponse res = new SimpleResponse();

		Article article= articleService.findById(art.getId());
		Cart cart = cartService.findByCustomerId(id);
		if (cart==null) {
			res.status = SimpleResponse.Status.ERROR;
			res.message = "Ce panier n'existe pas";
		} else {

			for (CartElement element : cart.getElements()) {
				if (element.getArticle().getId()==article.getId()) {
					element.setQuantite(art.getQty());
				}
			}
			cartService.save(cart);

			System.out.println(
					"********************\n"
							+ "***** " + String.format("Update Article [%d] quantity %d in cart", article.getId(), art.getQty()) + "\n"
							+ "********************"
			);

			res.status = SimpleResponse.Status.OK;
		}

		return res;
	}

	@ResponseBody
	@PostMapping(path="/{id}/remove.json",consumes="application/json")
	public SimpleResponse remove(@PathVariable(name="id") int id, @RequestBody CartEntry art) throws DataException {

		SimpleResponse res = new SimpleResponse();

		Cart cart = cartService.findByCustomerId(id);
		if (cart==null) {

			res.status = SimpleResponse.Status.ERROR;
			res.message = "Ce panier n'existe pas";
			return res;
		} else {

			for (CartElement ce: cart.getElements()) {
				if (ce.getArticle().getId()==art.getId()) {

					cart.getElements().remove(ce);
					System.out.println(
							"********************\n"
									+ "***** " + String.format("Remove Article [%d] from cart", art.getId()) + "\n"
									+ "********************"
					);
					cartService.save(cart);

					res.status = SimpleResponse.Status.OK;
					return res;

				}

			}

			res.status = SimpleResponse.Status.ERROR;
			res.message = "Cet article n'est pas dans le panier";
			return res;

		}

	}

	@ResponseBody
	@PostMapping(path="/{id}/promo-code")
	public SimpleResponse addPromoCode(@PathVariable(name="id") int id, @RequestBody String code) throws DataException {

		SimpleResponse res = new SimpleResponse();

		Cart cart = cartService.findByCustomerId(id);
		if (cart==null) {
			res.status = SimpleResponse.Status.ERROR;
			res.message = "Ce panier n'existe pas";
			return res;
		} else {

			// Add promo to cart articles
			Promo promo = promoService.findByCode(code);

			for (CartElement ce: cart.getElements()) {
				ce.getArticle().setPromo(promo);
			}

			cartService.save(cart);

			// Set promo as used
			Customer customer = customerService.findById(id);
			UsedPromo usedPromo = new UsedPromo(customer, promo);
			used_PromoService.save(usedPromo);

			res.status = SimpleResponse.Status.OK;
			return res;
		}

	}

	@RequestMapping("/{id}/validate")
	public String validateCart(@PathVariable(name="id") int id, Model model) {
		return "redirect:/articles";
	}

//	@RequestMapping("/{id}/validate.html")
//	public String validateCart(@PathVariable(name="id") int id, Model model) throws DataException {
//
//		// get cart by its id
//		try {
//			Cart cart = daoCart.getCartContent(id);
//			if (cart == null) {
//				throw new DataException("Cart is null");
//			}
//			// create an order
//			Order order = new Order();
//
//
//			// set order date
//			order.setCreatedOn(new Date());
//			order.setCurrentStatus(fr.eservices.drive.dao.Status.ORDERED);
//			order.setCustomerId("chuckNorris");
//			List<String>articles = new ArrayList<String>();
//			int price =0;
//			for(Article article : cart.getArticles()) {
//				articles.add(article.getId());
//				price += article.getPrice();
//			}
//			order.setArticles(articles);
//			// for each article, add it to the order
//
//			// set order amount (sum of each articles' price)
//			order.setAmount(price);
//			orderRepository.save(order);
//			// persist everything
//			model.addAttribute("cart", cart);
//
//			// redirect user to list of orders
//			return "redirect:order/ofCustomer/chuckNorris.html";
//
//		} catch (DataException e) {
//			throw new DataException(e.getMessage());
//		}
//	}
}
