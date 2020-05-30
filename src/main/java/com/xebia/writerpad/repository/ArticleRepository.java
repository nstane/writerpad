package com.xebia.writerpad.repository;

import com.xebia.writerpad.entity.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, String>
{
    @Query(value = "SELECT tags as tag , count(*) as occurrence FROM ARTICLE_TAGS group by tags", nativeQuery = true)
    List<Object> findTagsMetrics();

    @Query(value = "select a.id, a.body from ARTICLE a where a.id = ?1")
    Object findArticleIdAndBodyById(final String id);

    @Query(value = "select a.body from ARTICLE a")
    Stream<String> getArticlesBodyStream();
}
