package fr.eservices.promos.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eservices.promos.model.Article;
import fr.eservices.promos.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import fr.eservices.promos.model.Promo;
import fr.eservices.promos.service.PromoService;
import fr.eservices.promos.service.PromoTypeService;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    PromoTypeService promoTypeService;

    @Autowired
    ArticleService articleService;

    @Autowired
    PromoService promoService;

    List<Integer> selectedArticles = new ArrayList<>();

    @GetMapping(path = "/promos")
    public String managePromos(Model model) {
        selectedArticles.clear();
        model.addAttribute("Promo", new Promo());
        model.addAttribute("promos", promoService.findAll());
        model.addAttribute("promoTypes", promoTypeService.findAll());
        model.addAttribute("articles", articleService.findAll());
        return "admin_promos";
    }

    @PostMapping(path = "/promo")
    public String addPromo(@ModelAttribute Promo promo, Model model) {
        if(!selectedArticles.isEmpty()) {
            if (promo.getStart() != null && promo.getEnd() != null && promo.getEnd().compareTo(promo.getStart()) >= 0) {
                promoService.saveOrUpdate(promo);
                for (Article article : articleService.findAll()) {
                    if (selectedArticles.contains(article.getId())) {
                        article.setPromo(promo);
                        articleService.saveOrUpdate(article);
                    }
                }
                return "redirect:promos";
            }
            return "redirect:promos?erreur=date";
        }
        return "redirect:promos?erreur=article";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ResponseBody
    @PostMapping(path="/match")
    public List<Article> getArticleMatching(@RequestBody String inputArticle) {
        if(inputArticle.length() > 0)
            return articleService.find(inputArticle.substring(0, inputArticle.length()-1));
        return null;
    }

    @ResponseBody
    @PostMapping(path="/selectArticle")
    public int updateSelectedArticlesList(@RequestBody String idArticle) {
        int idArticleAsInt = Integer.parseInt(idArticle.substring(0, idArticle.length()-1));
        if(selectedArticles.contains(idArticleAsInt)){
            selectedArticles.remove(Integer.valueOf(idArticleAsInt));
            return 0;
        }
        selectedArticles.add(idArticleAsInt);
        return 1;
    }
}
