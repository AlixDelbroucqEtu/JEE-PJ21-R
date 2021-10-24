package fr.eservices.promos.controller;

import java.util.List;
import java.util.Optional;


import fr.eservices.promos.model.Customer;
import fr.eservices.promos.model.UsedPromo;
import fr.eservices.promos.repository.CustomerRepository;
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
    private CustomerRepository customerRepository;

    @RequestMapping(path = "/{custId}.html")
    public String list(@PathVariable Integer custId, Model model) {

        // use repo to get orders of a customer 
        Customer customer = customerRepository.findById(custId).get();
        //TODO : récupérer les promos a partir du répository
        List<UsedPromo> promo_used = null;
        // assign in model as "orders"
        model.addAttribute("customer", customer);
        // return order list view

        return "order_list";
    }

}
