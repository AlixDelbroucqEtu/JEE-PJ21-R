package fr.eservices.promos.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int customerId;

    @ManyToMany(cascade = CascadeType.ALL)
    List<CartElement> elements;

    public Cart(int customerId) {
        this.customerId = customerId;
        this.elements = new ArrayList<CartElement>();
    }

    public Cart() {}

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<CartElement> getElements() {
        return elements;
    }

    public void setElements(List<CartElement> elements) {
        this.elements = elements;
    }
}
