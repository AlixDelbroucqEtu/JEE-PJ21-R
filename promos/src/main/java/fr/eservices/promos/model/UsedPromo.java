package fr.eservices.promos.model;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="used_promos")
@IdClass(UsedPromoPk.class)
public class UsedPromo implements Serializable {
    @Id
    private String customerId;

    @Id
    private String promoId;

}
