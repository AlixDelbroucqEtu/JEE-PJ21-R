package fr.eservices.promos.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "promos")
public class Promo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "type")
    private PromoType promoType;

    private float x;

    private float y;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date start;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date end;

    @Column(name = "customer_limit")
    private int customerLimit;

    private String code;

    @Column(name = "on_cart")
    private Boolean onCart;

    public Boolean isDateValid() {
        Date today = new Date();
        return today.compareTo(start) >= 0 && today.compareTo(end) <= 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PromoType getPromoType() {
        return promoType;
    }

    public void setPromoType(PromoType promoType) {
        this.promoType = promoType;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getCustomerLimit() {
        return customerLimit;
    }

    public void setCustomerLimit(int customerLimit) {
        this.customerLimit = customerLimit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getOnCart() {
        return onCart;
    }

    public void setOnCart(Boolean onCart) {
        this.onCart = onCart;
    }
}
