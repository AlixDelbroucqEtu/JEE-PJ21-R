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
    @GetMapping(path = "/marketingCampain")
    public String manageMaketingCampain(Model model) {
        model.addAttribute("Promo", new Promo());
        model.addAttribute("promos", promoService.findAll());
        model.addAttribute("marketing_campain", promoService.findMarketingCampain());
        return "admin_marketing_campain";
    }

    @PostMapping(path = "/promo")
    public String addPromo(@ModelAttribute Promo promo, Model model) {
        if(!selectedArticles.isEmpty()) {
            if (promo.getStart() != null && promo.getEnd() != null && promo.getEnd().compareTo(promo.getStart()) >= 0) {
                switch(promo.getPromoType().getType()){
                    case "PROMOTION":
                        promo.setY(0);
                        promo.setCode(null);
                        promo.setCustomerLimit(0);
                        break;
                    case "OFFRE MARKETING":
                        switch(promo.getPromoType().getName()){
                            case "Le 2ème à X%":
                            case "X+1 gratuit":
                                promo.setY(0);
                            case "Le lot de X à Y€":
                                if(promo.getX()%1 != 0)
                                    return "redirect:promos?erreur=xint";
                                break;
                        }
                        break;
                }
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

    @ResponseBody
    @PostMapping(path="/adaptForm")
    public String adaptForm(@RequestBody String promoType) {
        switch(promoType.charAt(0)){
            case '1':
                return "<div class='form-group'>\n" +
                        "                <label>Pourcentage</label>\n" +
                        "                <input type='number' min='0' max='100' step='.01' class='form-control' id='x' name='x'/>\n" +
                        "            </div>";
            case '2':
                return "<div class='form-group'>\n" +
                        "                <label>Valeur</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' step='.01' id='x' name='x'/>\n" +
                        "            </div>";
            case '3':
                return "<div class='form-group'>\n" +
                        "                <label>Pourcentage</label>\n" +
                        "                <form type='number' min='0' max='100' step='.01' class='form-control' id='x' name='x'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label>Nombre de clients max</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='customerLimit' name='customerLimit'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label>Code</label>\n" +
                        "                <input class='form-control' maxlength='20' id='code' name='code'/>\n" +
                        "            </div>";
            case '4':
                return "<div class='form-group'>\n" +
                        "                <label>Valeur</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' step='.01' id='x' name='x'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label>Nombre de clients max</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='customerLimit' name='customerLimit'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label>Code</label>\n" +
                        "                <input class='form-control' maxlength='20' id='code' name='code'/>\n" +
                        "            </div>";
            case '5':
                return "<div class='form-group'>\n" +
                        "                <label>Valeur X</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='x' name='x'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label>Nombre de clients max</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='customerLimit' name='customerLimit'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label>Code</label>\n" +
                        "                <input class='form-control' maxlength='20' id='code' name='code'/>\n" +
                        "            </div>";
            case '6':
                return "<div class='form-group'>\n" +
                        "                <label>Pourcentage</label>\n" +
                        "                <input type='number' min='0' max='100' step='.01' class='form-control' id='x' name='x'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label>Nombre de clients max</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='customerLimit' name='customerLimit'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label>Code</label>\n" +
                        "                <input class='form-control' maxlength='20' id='code' name='code'/>\n" +
                        "            </div>";
            case '7':
                return "<div class='form-group'>\n" +
                        "                <label>Valeur X</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='x' name='x'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label>Valeur Y</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' step='.01' id='y' name='y'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label>Nombre de clients max</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='customerLimit' name='customerLimit'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label>Code</label>\n" +
                        "                <input class='form-control' maxlength='20' id='code' name='code'/>\n" +
                        "            </div>";
            default:
                return "";
        }
    }
}
