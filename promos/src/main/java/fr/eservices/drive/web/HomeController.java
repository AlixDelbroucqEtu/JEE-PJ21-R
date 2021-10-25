package fr.eservices.drive.web;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.eservices.drive.dao.ArticleDao;
import fr.eservices.drive.dao.CartDao;
import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Cart;

@Controller
@RequestMapping(path="")
public class HomeController {
	
	@Autowired
	ArticleDao daoArticle;
	
	@GetMapping(path="/articles")
	public String getCart(Model model) throws DataException {
		HashMap<String, Article> articles = daoArticle.getArticles();
		model.addAttribute("daoArticle",daoArticle);
		model.addAttribute("articles",articles.values());
		return "products";
	}
}
