package fr.eservices.promos.service;

import fr.eservices.promos.model.Customer;
import fr.eservices.promos.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    public Customer findById(Integer id){
        Optional<Customer> customer= customerRepository.findById(id);
        if ( customer.isPresent()) {
            return customer.get();
        }
        return null;
    }
}
