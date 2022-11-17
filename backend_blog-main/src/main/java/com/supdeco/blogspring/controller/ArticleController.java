package com.supdeco.blogspring.controller;

import com.supdeco.blogspring.register.ArticleRegister;
import com.supdeco.blogspring.security.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article/")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity createArticle(@RequestBody ArticleRegister articleRegister) {
        articleService.createPost(articleRegister);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArticleRegister>> showAllArticle() {
        return new ResponseEntity<>(articleService.showAllArticle(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ArticleRegister> getSingleArticle(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(articleService.readSingleArticle(id), HttpStatus.OK);
    }
}
