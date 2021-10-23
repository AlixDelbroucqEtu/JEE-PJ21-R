package fr.eservices.promos.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="used_promos")
public class UsedPromo {

    private Long id;

    @ManyToMany
    private List<Customer> customers;

    @ManyToMany(mappedBy = "customers")
    private List<Promo> promos;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Promo> getPromos() {
        return promos;
    }

    public void setPromos(List<Promo> promos) {
        this.promos = promos;
    }
}
