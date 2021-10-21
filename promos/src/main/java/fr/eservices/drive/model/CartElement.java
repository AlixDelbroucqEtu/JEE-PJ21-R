package fr.eservices.drive.model;

public class CartElement {

	Article article;
	
	private int quantite;

	public CartElement(Article article, int quantite) {
		super();
		this.article = article;
		this.quantite = quantite;
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
