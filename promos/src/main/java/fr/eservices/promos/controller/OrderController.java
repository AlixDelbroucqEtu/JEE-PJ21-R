package fr.eservices.promos.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eservices.promos.model.Order;
import fr.eservices.promos.repository.OrderRepository;


@Controller
@RequestMapping(path="/order")
public class OrderController {

    @Autowired
    private OrderRepository repoOrder;

    @RequestMapping(path="/ofCustomer/{custId}.html")
    public String list(@PathVariable String custId, Model model) {

        // use repo to get orders of a customer
        List<Order> orders = repoOrder.findByCustomerIdOrderByCreatedOnDesc(custId);
        // assign in model as "orders"
        model.addAttribute("orders", orders);
        // return order list view

        return "order_list";
    }

}