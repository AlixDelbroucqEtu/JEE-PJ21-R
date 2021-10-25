package fr.eservices.drive.dao;

import java.util.HashMap;

import fr.eservices.drive.model.Article;

public interface ArticleDao {
	
	Article find(String id);
	
	HashMap<String, Article> getArticles();
	
	boolean isInCart(String articleId, int cartId) throws DataException;

}
