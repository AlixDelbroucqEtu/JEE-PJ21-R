package fr.eservices.promos.model;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

import fr.eservices.promos.repository.PromoRepository;
import fr.eservices.promos.service.PromoService;

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

    public double getPriceAfterPromo (String code) {
        Promo promo = article.getPromo();
        double total = article.getPrice() * quantite;

        // No promo to consider
        if (promo == null || !promo.isDateValid()) {
            return total;
        }

        // Any promo code ?
        if (code != null) {
            PromoService promoService = new PromoService();
            promo = promoService.findByCode(code);
        }

        // Marketing
        switch (promo.getPromoType().getId()) {
            case 5: // X + 1 gratuit
                if (quantite >= promo.getX()) {
                    total = total - article.getPrice();
                    break;
                }
            case 6: // 2eme a X %
                if (quantite >= 2) {
                    total = total - (article.getPrice() * (promo.getX() / 100));
                    break;
                }
            case 7:
                if (quantite >= promo.getX()) {
                    total = total - (promo.getX() * article.getPrice()) + promo.getY();
                    break;
                }
            case 4: // Valeur fixe panier
                total = total - promo.getX();
                break;
            case 3: // Pourcentage panier
                total = total - (total * (promo.getX() / 100));
                break;
        }

        // Promo
        switch (promo.getPromoType().getId()) {
            case 1: // Pourcentage produit
                total = total - (quantite * (article.getPrice() * (promo.getX() / 100)));
                break;
            case 2: // Valeur absolue produit
                total = total - (quantite * promo.getX());
        }

        return total;
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