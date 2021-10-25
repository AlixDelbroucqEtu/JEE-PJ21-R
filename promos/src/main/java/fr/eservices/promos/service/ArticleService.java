package fr.eservices.promos.service;

import fr.eservices.promos.model.Article;
import fr.eservices.promos.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    
    @Autowired
    ArticleRepository articleRepository;

    public List<Article> find(String inputArticle) {
        return articleRepository.findByReferenceStartsWithIgnoreCaseOrLibelleStartsWithIgnoreCaseOrMarqueStartsWithIgnoreCase(inputArticle, inputArticle, inputArticle);
    }

    public List<Article> findAll() {
        return (List<Article>) articleRepository.findAll();
    }

    public void saveOrUpdate(Article article) {
        articleRepository.save(article);
    }

    public void delete(int id) {
        articleRepository.deleteById(id);
    }
}

