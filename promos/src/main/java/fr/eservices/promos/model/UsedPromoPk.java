package fr.eservices.promos.model;

import java.io.Serializable;

public class UsedPromoPk implements Serializable {

    private String customerId;

    private String promoId;

    public UsedPromoPk(){
    }

    public UsedPromoPk(String customerId, String promoId) {
        this.customerId = customerId;
        this.promoId = promoId;
    }

}
