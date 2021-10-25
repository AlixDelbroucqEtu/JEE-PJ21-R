package fr.eservices.promos.model;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="used_promo")
@IdClass(UsedPromoPk.class)
public class UsedPromo implements Serializable {
    @ManyToOne(cascade = CascadeType.PERSIST)
    @Id
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @Id
    @JoinColumn(name="promo_id")
    private Promo promo;

    public UsedPromo(Customer customer, Promo promo) {
        this.customer = customer;
        this.promo = promo;
    }

    public UsedPromo() {

    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Promo getPromo() {
        return promo;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }
}
