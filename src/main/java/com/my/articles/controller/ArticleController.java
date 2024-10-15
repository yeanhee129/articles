package com.my.articles.controller;

import com.my.articles.dto.ArticleDto;
import com.my.articles.entity.Article;
import com.my.articles.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    // 전체 목록 조회
    @GetMapping("")
    public String showAllArticles(Model model){
        model.addAttribute("articles", articleService.findAll());
        return "/articles/show_all";
    }

    // 새글 작성
    @GetMapping("new")
    public String newArticle(Model model){
        model.addAttribute("dto", new ArticleDto());
        return "/articles/new";
    }

    // 새글 작성에서 저장
    @PostMapping("create")
    public String createArticle(ArticleDto dto){
        articleService.insertArticle(dto);
        return "redirect:/articles";
    }

    // 특정 article 조회
    @GetMapping("{id}")
    public String showOneArticle(@PathVariable("id")Long id, Model model){
        model.addAttribute("dto", articleService.findById(id));
        return "/articles/show";
    }

    @GetMapping("{id}/update")
    public String viewUpdateArticle(@PathVariable("id")Long id, Model model){
        model.addAttribute("dto", articleService.findById(id));
        return "/articles/update";
    }

    @PostMapping("update")
    public String updateArticle(ArticleDto dto){
        // String url = "redirect:/articles/" + dto.getId();
        String url = "redirect:" + dto.getId();
        articleService.update(dto);
        return url;
    }

    @GetMapping("{id}/delete")
    public String deleteArticle(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        articleService.delete(id);
        redirectAttributes.addFlashAttribute("msg", "정상적으로 삭제되었습니다.");
        return "redirect:/articles";
    }
}
