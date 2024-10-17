package com.my.articles.controller;

import com.my.articles.dto.ArticleDto;
import com.my.articles.entity.Article;
import com.my.articles.repository.ArticleRepository;
import com.my.articles.service.ArticleService;
import com.my.articles.service.PagenationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @Autowired
    PagenationService pagenationService;

    // 전체 목록 조회
    @GetMapping("")
    public String showAllArticles(Model model, @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        // model.addAttribute("articles", articleService.findAll());
        Page<ArticleDto> paging = articleService.getArticlePage(pageable);

        // 페이징 정보 확인
        // 전체 페이지 수
        int totalPage = paging.getTotalPages();
        int currentPage = paging.getNumber();
        System.out.println("totalPage: " + totalPage + ", currentPage: " + currentPage);

        // 페이지 블럭 처리
        List<Integer> barNumbers = pagenationService.getPaginationBarNumber(currentPage, totalPage);
        System.out.println("barNumbers: " + barNumbers.toString());
        model.addAttribute("pageBars", barNumbers);
        model.addAttribute("articles", paging);
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
