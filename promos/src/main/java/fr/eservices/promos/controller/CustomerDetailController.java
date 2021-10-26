package fr.eservices.promos.controller;

import java.util.List;

import java.util.ArrayList;


import fr.eservices.promos.model.Customer;
import fr.eservices.promos.model.Promo;
import fr.eservices.promos.service.CustomerService;
import fr.eservices.promos.service.Used_PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


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
        List<Promo> promo;
        if(customer == null){
            customer = new Customer();
            customer.setId(42);
            customer.setPseudo("Unknow");
            promo = new ArrayList<Promo>();
        }
        else{
             promo = used_promoService.findByCustomer(customer.getId());
        }

        // assign in model as "orders"
        model.addAttribute("customer", customer);
        model.addAttribute("promos", promo);
        // return order list view

        return "customer_detail_list";
    }
}
