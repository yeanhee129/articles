package com.my.articles.dao;

import com.my.articles.dto.ArticleDto;
import com.my.articles.entity.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class ArticleDao {
    @Autowired
    EntityManager em;

    public List<Article> getAllArticle() {
        String sql = "SELECT a FROM Article a ORDER BY a.id";
        Query query = em.createQuery(sql);
        List<Article> articleList = query.getResultList();
        System.out.println(articleList);
        return articleList;
    }

    public Article findById(Long id) {
        return em.find(Article.class, id);
    }

    public void remove(Long id) {
        Article article = em.find(Article.class, id);
        em.remove(article);
    }

    public void update(ArticleDto dto) {
        Article article = em.find(Article.class, dto.getId());
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
    }

    public void insertArticle(Article article) {
        em.persist(article);
    }
}
