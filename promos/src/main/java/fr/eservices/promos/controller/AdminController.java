package fr.eservices.promos.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import fr.eservices.promos.model.Article;
import fr.eservices.promos.service.ArticleService;
import fr.eservices.promos.service.CategoryService;
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
    CategoryService categoryService;

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
        model.addAttribute("categories", categoryService.findAll());
        return "admin_promos";
    }
    @GetMapping(path = "/marketingCampaign")
    public String manageMaketingCampain(Model model) {
        model.addAttribute("Promo", new Promo());
        model.addAttribute("promos", promoService.findMarketingCampain());
        return "admin_marketing_campaign";
    }

    @PostMapping(path = "/promo")
    public String addPromo(@ModelAttribute Promo promo, Model model) {

        if (promo.getStart() != null && promo.getEnd() != null && promo.getEnd().compareTo(promo.getStart()) >= 0) {
            switch(promo.getPromoType().getType()){
                //UNE PROMOTION S'APPLIQUE A UN ARTICLE. IL S'AGIT D'UNE REDUCTION DE LA VALEUR D'UN ARTICLE EN POURCENTAGE OU EN EUROS.
                case "PROMOTION":
                    if(!selectedArticles.isEmpty()) {
                        promo.setOnCart(false);
                        promo.setY(0);
                        promo.setCode(null);
                        promo.setCustomerLimit(0);
                    }else {
                        return "redirect:promos?erreur=article";
                    }
                break;
                        //UNE OFFRE MARKETING S'APPLIQUE FORCEMENT A L'ENSEMBLE DU PANIER. ELLE PEUT DEPENDRE DE L'ACHAT D'UN ARTICLE PRECIS DANS UNE CERTAINE QUANTITE.
                        //ELLE PEUT ETRE APPLIQUEE SI UN CODE EST RENSEIGNE ET A UN NOMBRE LIMITE DE CLIENTS.
                case "OFFRE MARKETING":
                    switch(promo.getPromoType().getName()) {
                        case "Le 2ème à X%":
                        case "X+1 gratuit":
                            promo.setY(0);
                        case "Le lot de X à Y€":
                            if (!selectedArticles.isEmpty()) {
                                promo.setOnCart(true);
                            } else {
                                return "redirect:promos?erreur=article";
                            }
                            if (promo.getX() % 1 != 0)
                                return "redirect:promos?erreur=xint";
                            break;
                        default:
                            selectedArticles.clear();
                            promo.setOnCart(false);
                            promo.setY(0);
                    }
                break;
            }
            promoService.saveOrUpdate(promo);
            for (Article article : articleService.findAll()) {
                if (selectedArticles.contains(article.getId())) {
                    //PRIORITE DES PROMO SUR LES ARTICLES => 5 > 6 > 7 > 2 > 1
                    //LES OFFRES COMMERCIALES 3 ET 4 REPRESENTENT UNE REMISE BRUTE SUR PANIER SANS CONDITION DE SELECTION D'ARTICLES ET NE SONT DONC PAS LIE A UN OU DES ARTICLES
                        article.setPromo(promo);
                        articleService.saveOrUpdate(article);
                }
            }
            return "redirect:promos";
        }
        return "redirect:promos?erreur=date";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ResponseBody
    @PostMapping(path="/match")
    public List<Article> getArticleMatching(@RequestBody Map<String, String> data) {
        if(data.get("input").length() > 0) {
            if(Integer.parseInt(data.get("idcat")) == 0){
                return articleService.find(data.get("input").substring(0, data.get("input").length()-1));
            }else{
                return articleService.findWhereCategory(data.get("input").substring(0, data.get("input").length()-1), Integer.parseInt(data.get("idcat")));
            }
        }
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
    @PostMapping(path="/removePromo")
    public void removePromo(@RequestBody String idPromo) {
        int idPromoAsInt = Integer.parseInt(idPromo.substring(0, idPromo.length()-1));
        promoService.delete(idPromoAsInt);
    }


    @ResponseBody
    @PostMapping(path="/adaptForm")
    public String adaptForm(@RequestBody String promoType) {
        switch(promoType.charAt(0)){
            case '1':
                return "             <div class='form-group'>\n" +
                        "                <label for='inputArticles'>Article(s) concerné(s)</label>\n" +
                        "                <input onKeyUp='searchArticles()' id='inputArticles' class='form-control' type='text' maxlength='30' placeholder='Chercher un article...'/>\n" +
                        "            </div>\n" +
                        "            <div id='selectedArticles'>\n" +
                        "\n" +
                        "            </div><div class='form-group'>\n" +
                        "                <label for='x'>Pourcentage</label>\n" +
                        "                <input type='number' min='0' max='100' step='.01' class='form-control' id='x' name='x'/>\n" +
                        "            </div>";
            case '2':
                return "             <div class='form-group'>\n" +
                        "                <label for='inputArticles'>Article(s) concerné(s)</label>\n" +
                        "                <input onKeyUp='searchArticles()' id='inputArticles' class='form-control' type='text' maxlength='30' placeholder='Chercher un article...'/>\n" +
                        "            </div>\n" +
                        "            <div id='selectedArticles'>\n" +
                        "\n" +
                        "            </div><div class='form-group'>\n" +
                        "                <label for='x'>Valeur</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' step='.01' id='x' name='x'/>\n" +
                        "            </div>";
            case '3':
                return "" +
                        "            <div class='form-group'>\n" +
                        "                <label for='x'>Pourcentage</label>\n" +
                        "                <input type='number' min='0' max='100' step='.01' class='form-control' id='x' name='x'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label for='customerLimit'>Nombre de clients max</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='customerLimit' name='customerLimit'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label for='code'>Code</label>\n" +
                        "                <input class='form-control' maxlength='20' id='code' name='code'/>\n" +
                        "            </div>";
            case '4':
                return "" +
                        "            <div class='form-group'>\n" +
                        "                <label for='x'>Valeur</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' step='.01' id='x' name='x'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label for='customerLimit'>Nombre de clients max</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='customerLimit' name='customerLimit'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label for='code'>Code</label>\n" +
                        "                <input class='form-control' maxlength='20' id='code' name='code'/>\n" +
                        "            </div>";
            case '5':
                return "             <div class='form-group'>\n" +
                        "                <label for='inputArticles'>Article(s) concerné(s)</label>\n" +
                        "                <input onKeyUp='searchArticles()' id='inputArticles' class='form-control' type='text' maxlength='30' placeholder='Chercher un article...'/>\n" +
                        "            </div>\n" +
                        "            <div id='selectedArticles'>\n" +
                        "\n" +
                        "            </div><div class='form-group'>\n" +
                        "                <label for='x'>Valeur X</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='x' name='x'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label for='customerLimit'>Nombre de clients max</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='customerLimit' name='customerLimit'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label for='code'>Code</label>\n" +
                        "                <input class='form-control' maxlength='20' id='code' name='code'/>\n" +
                        "            </div>";
            case '6':
                return "             <div class='form-group'>\n" +
                        "                <label for='inputArticles'>Article(s) concerné(s)</label>\n" +
                        "                <input onKeyUp='searchArticles()' id='inputArticles' class='form-control' type='text' maxlength='30' placeholder='Chercher un article...'/>\n" +
                        "            </div>\n" +
                        "            <div id='selectedArticles'>\n" +
                        "\n" +
                        "            </div><div class='form-group'>\n" +
                        "                <label for='x'>Pourcentage</label>\n" +
                        "                <input type='number' min='0' max='100' step='.01' class='form-control' id='x' name='x'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label for='customerLimit'>Nombre de clients max</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='customerLimit' name='customerLimit'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label for='code'>Code</label>\n" +
                        "                <input class='form-control' maxlength='20' id='code' name='code'/>\n" +
                        "            </div>";
            case '7':
                return "             <div class='form-group'>\n" +
                        "                <label for='inputArticles'>Article(s) concerné(s)</label>\n" +
                        "                <input onKeyUp='searchArticles()' id='inputArticles' class='form-control' type='text' maxlength='30' placeholder='Chercher un article...'/>\n" +
                        "            </div>\n" +
                        "            <div id='selectedArticles'>\n" +
                        "\n" +
                        "            </div><div class='form-group'>\n" +
                        "                <label for='x'>Valeur X</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='x' name='x'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label for='y'>Valeur Y</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' step='.01' id='y' name='y'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label for='customerLimit'>Nombre de clients max</label>\n" +
                        "                <input class='form-control' type='number' min='0' max='1000000' id='customerLimit' name='customerLimit'/>\n" +
                        "            </div>\n" +
                        "            <div class='form-group'>\n" +
                        "                <label for='code'>Code</label>\n" +
                        "                <input class='form-control' maxlength='20' id='code' name='code'/>\n" +
                        "            </div>";
            default:
                return "";
        }
    }
}
