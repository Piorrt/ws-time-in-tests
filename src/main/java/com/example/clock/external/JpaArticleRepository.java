package com.example.clock.external;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaArticleRepository extends JpaRepository<ArticleEntity, Long> {
}