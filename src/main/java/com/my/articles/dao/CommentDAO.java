package com.my.articles.dao;

import com.my.articles.entity.Article;
import com.my.articles.entity.Comment;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class CommentDAO {
    @Autowired
    EntityManager em;

    public Long deleteComment(Long id) {
        Comment comment = em.find(Comment.class, id);
        em.remove(comment);
        return comment.getArticle().getId();
    }

    public void insertComment(Long articleId, Comment comment) {
        Article article = em.find(Article.class, articleId);
        comment.setArticle(article);
        article.getComments().add(comment);
        em.persist(article);
    }

    public Comment findByIdComment(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    public void updateComment(Comment comment) {
        Comment updateCommnet = em.find(Comment.class, comment.getId());
        updateCommnet.setBody(comment.getBody());
    }
}
