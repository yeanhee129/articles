package com.my.articles.dto;

import com.my.articles.entity.Article;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;

    // Entity -> DTO
    public static ArticleDto fromEntity(Article article) {
        return new ArticleDto(
                article.getId(),
                article.getTitle(),
                article.getContent()
        );
    }

    // DTO -> Entity
    public static Article fromDTO(ArticleDto dto){
        Article article = new Article();
            article.setId(dto.getId());
            article.setTitle(dto.getTitle());
            article.setContent(dto.getContent());
            return article;
    }
}
