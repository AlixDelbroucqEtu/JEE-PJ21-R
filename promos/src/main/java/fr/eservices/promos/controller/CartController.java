package fr.eservices.promos.controller;

import java.io.ByteArrayOutputStream;


import java.io.PrintWriter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.eservices.promos.dao.CartDao;
import fr.eservices.promos.exception.DataException;
import fr.eservices.promos.model.Cart;

@Controller
@RequestMapping(path="/cart")
public class CartController {

	@Autowired
	CartDao daoCart;

//	@Autowired
//	OrderRepository orderRepository;
//
//	@Autowired
//	ArticleDao daoArticle;

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

//	@ResponseBody
//	@PostMapping(path="/{id}/add.json",consumes="application/json")
//	public SimpleResponse add(@PathVariable(name="id") int id, @RequestBody CartEntry art) throws DataException {
//		ArticleMockDao articleMockDao = new ArticleMockDao();
//		SimpleResponse res = new SimpleResponse();
//		System.out.println(
//				"********************\n"
//						+ "***** " + String.format("Add Article %d x [%s] to cart", art.getQty(), art.getId()) + "\n" 
//						+ "********************"
//				);
//		if (art.getQty() <0) {
//			res.status = Status.ERROR;
//			res.message = "La quantité de l'article doit être positive";
//			return res;
//		}
//		try {
//			Article article= articleMockDao.find(art.getId());
//			if(article== null) {
//				res.status = Status.ERROR;
//				res.message = "Cet article n'existe pas";
//				return res;
//			}
//			else {			
//				Cart cart = daoCart.getCartContent(id);
//				if (cart == null) {
//					cart = new Cart();
//					daoCart.store(id, cart);
//				}
//				
//				List<Article> articles = cart.getArticles();
//				if(articles == null) {
//					articles = new ArrayList<Article>();
//				}
//				for (int i=0;i< art.getQty(); i++) {
//					articles.add(article);
//				}
//				cart.setArticles(articles);
//				res.status= Status.OK;
//				return res;
//
//			}
//		} catch (DataException e) {
//			throw new DataException("Cet article n'existe pas \n"+e.getMessage());
//		}
//		
//	}
//
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
