package com.my.articles.service;

import com.my.articles.dao.ArticleDao;
import com.my.articles.dto.ArticleDto;
import com.my.articles.entity.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    ArticleDao dao;

    public List<ArticleDto> findAll() {
        List<Article> articles = dao.getAllArticle();
        if(ObjectUtils.isEmpty(articles)){
            return Collections.emptyList();
        }
        List<ArticleDto> dtoList = articles.stream().map(x->ArticleDto.fromEntity(x)).toList();
        return dtoList;
    }

    public ArticleDto findById(Long id) {
        Article article = dao.findById(id);
        if(ObjectUtils.isEmpty(article)){
            return null;
        }
        return ArticleDto.fromEntity(article);
    }

    public void delete(Long id) {
        dao.remove(id);
    }

    public void update(ArticleDto dto) {
        dao.update(dto);
    }

    public void insertArticle(ArticleDto dto) {
        dao.insertArticle(ArticleDto.fromDTO(dto));
    }
}
