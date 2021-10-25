package fr.eservices.promos.controller;

import java.util.List;
import java.util.Optional;


import fr.eservices.promos.model.Customer;
import fr.eservices.promos.model.UsedPromo;
import fr.eservices.promos.repository.CustomerRepository;
import fr.eservices.promos.repository.UsedPromoRepository;
import fr.eservices.promos.service.CustomerService;
import fr.eservices.promos.service.Used_PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eservices.promos.model.Order;
import fr.eservices.promos.repository.OrderRepository;


@Controller
@RequestMapping(path = "/customer_detail")
public class CustomerDetailController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private Used_PromoService used_promoService;

    @RequestMapping(path = "/{custId}.html")
    public String list(@PathVariable Integer custId, Model model) {

        // use repo to get orders of a customer 
        Customer customer = customerService.findById(custId);
        List<UsedPromo> promo_used = used_promoService.findAllByCustomer_Id(custId);
        // assign in model as "orders"
        model.addAttribute("customer", customer);
        model.addAttribute("used_promo", promo_used);
        // return order list view

        return "customer_detail_list";
    }
}
