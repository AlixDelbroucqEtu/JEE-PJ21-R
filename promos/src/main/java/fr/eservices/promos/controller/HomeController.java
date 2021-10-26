package fr.eservices.promos.controller;

import fr.eservices.promos.exception.DataException;
import fr.eservices.promos.model.Article;
import fr.eservices.promos.service.ArticleService;
import fr.eservices.promos.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path="")
public class HomeController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CartService cartService;

    @GetMapping(path="/articles")
    public String getCart(Model model) throws DataException {
        List<Article> articles = articleService.findAll();
        model.addAttribute("cartService",cartService);
        model.addAttribute("articles",articles);
        return "products";
    }
}
