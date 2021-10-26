package fr.eservices.promos.service;

import fr.eservices.promos.model.Article;
import fr.eservices.promos.model.Category;
import fr.eservices.promos.repository.ArticleRepository;
import fr.eservices.promos.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

}

