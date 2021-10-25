package fr.eservices.promos.model;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

public class UsedPromoPk implements Serializable {
    private long customer;
    private long promo;

    public UsedPromoPk() {
    }

    public UsedPromoPk(long customer, long promo) {
        this.customer = customer;
        this.promo = promo;
    }
}
