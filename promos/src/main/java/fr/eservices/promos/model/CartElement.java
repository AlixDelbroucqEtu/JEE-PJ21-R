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