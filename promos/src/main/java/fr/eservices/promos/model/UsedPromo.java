package fr.eservices.promos.model;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="used_promos")
@IdClass(UsedPromoPk.class)
public class UsedPromo implements Serializable {
    @ManyToOne(cascade = CascadeType.PERSIST)
    @Id
    private Customer customer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @Id
    private Promo promo;
}
