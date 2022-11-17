package com.supdeco.blogspring.security;

import com.supdeco.blogspring.model.Article;
import com.supdeco.blogspring.register.ArticleRegister;
import com.supdeco.blogspring.exception.PostNotFoundException;
import com.supdeco.blogspring.repository.ArticleRepository;
import com.supdeco.blogspring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ArticleService {

    @Autowired
    private AuthService authService;
    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    public List<ArticleRegister> showAllArticle() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::mapFromArticleToRegister).collect(toList());
    }

    @Transactional
    public void createPost(ArticleRegister articleRegister) {
        Article article = mapFromArticleToRegister(articleRegister);
        articleRepository.save(article);
    }

    @Transactional
    public ArticleRegister readSingleArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromArticleToRegister(article);
    }

    private ArticleRegister mapFromArticleToRegister(Article article) {
        ArticleRegister articleRegister = new ArticleRegister();
        articleRegister.setId(article.getId());
        articleRegister.setTitle(article.getTitle());
        articleRegister.setContent(article.getContent());
        articleRegister.setUsername(article.getUsername());
        return articleRegister;
    }

    private Article mapFromArticleToRegister(ArticleRegister articleRegister) {
        Article article = new Article();
        article.setTitle(articleRegister.getTitle());
        article.setContent(articleRegister.getContent());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("Utilisateur Not Found"));
        article.setCreatedOn(Instant.now());
        article.setUsername(loggedInUser.getUsername());
        article.setUpdatedOn(Instant.now());
        return article;
    }
}
