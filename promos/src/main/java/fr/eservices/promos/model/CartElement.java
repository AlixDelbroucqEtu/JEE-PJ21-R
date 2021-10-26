package fr.eservices.promos.model;

import javax.persistence.*;

@Entity
public class CartElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    Article article;

    private int quantite;

    public CartElement(Article article, int quantite) {
        super();
        this.article = article;
        this.quantite = quantite;
    }

    public CartElement() {}

    public double getPriceAfterPromo () {
        Promo promo = article.getPromo();
        double defaultTotal = article.getPrice() * quantite;
        // No promo to consider
        if (promo == null || !promo.isDateValid()) {
            return defaultTotal;
        }

        switch (promo.getPromoType().getId()) {
            case 1: // product absolute value
            case 3:
                defaultTotal = defaultTotal - promo.getX() * quantite;
            case 2: // product percentage
            case 4:
                defaultTotal = defaultTotal - (defaultTotal * (promo.getX() / 100)) * quantite;
            case 5: // x + 1 gratuit
                if (quantite < promo.getX()) return defaultTotal;
                return defaultTotal - article.getPrice();
            case 6: // 2ème à X%
                if (quantite < 2) return defaultTotal;
                return  defaultTotal - (defaultTotal * (promo.getX() / 100));
            case 7: // lot de x à y €
                if (quantite < promo.getX()) return defaultTotal;
                return defaultTotal - (article.getPrice() * promo.getX()) + promo.getY();
            default:
                return defaultTotal;
        }
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }


}