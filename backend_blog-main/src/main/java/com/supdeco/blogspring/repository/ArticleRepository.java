package com.supdeco.blogspring.repository;


import com.supdeco.blogspring.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
