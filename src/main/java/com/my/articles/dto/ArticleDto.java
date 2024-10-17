package com.my.articles.dto;

import com.my.articles.entity.Article;
import com.my.articles.entity.Comment;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comments;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private List<CommentDTO> comments = new ArrayList<>();

    public static ArticleDto fromEntity(Article article) {
        return new ArticleDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getComments()
                        .stream()
                        .map(x->CommentDTO.fromEntity(x))
                        .toList()
        );
    }

    public static Article fromDTO(ArticleDto dto) {
        Article article = new Article();
        article.setId(dto.getId());
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        return article;
    }
}
