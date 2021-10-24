package fr.eservices.promos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eservices.promos.model.Promo;
import fr.eservices.promos.service.PromoService;
import fr.eservices.promos.service.PromoTypeService;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    PromoTypeService promoTypeService;

    @Autowired
    PromoService promoService;

    @GetMapping(path = "/promos")
    public String managePromos(Model model) {
        model.addAttribute("Promo", new Promo());
        model.addAttribute("promos", promoService.findAll());
        model.addAttribute("promoTypes", promoTypeService.findAll());
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
        promoService.saveOrUpdate(promo);
        return "redirect:promos";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
