package com.xebia.writerpad.repository;

import com.xebia.writerpad.Constants;
import com.xebia.writerpad.entity.Article;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository repository;

    private Article article;

    @BeforeEach
    public void setup() {
        article = createArticle();
    }

    @Test
    public void findByIdTest() {
        getAndVerify(article);
    }

    @Test
    @Transactional
    public void updateTest() {
        String updatedTitle = "Test";
        article = updateArticle(updatedTitle, article);

        Optional<Article> optionalArticle = getAndVerify(article);
        Assertions.assertEquals(optionalArticle.get().getTitle(), updatedTitle);
        Assertions.assertEquals(optionalArticle.get().getTags(), null);
    }

    @Test
    public void deleteTest() {
        Optional<Article> optionalArticle = getAndVerify(article);
        repository.delete(optionalArticle.get());
        assertEquals(repository.existsById(optionalArticle.get().getId()), false);
    }

    @Test
    public void findArticleBodyIdTest() {
        Object[] data = (Object[]) repository.findArticleIdAndBodyById(article.getId());
        assertEquals(data[0], article.getId());
        assertEquals(data[1], article.getBody());
    }

    @Test
    public void tagMetricsTest() {
        List<Object> tagMetrics = repository.findTagsMetrics();
        Object[] data = (Object[]) tagMetrics.get(0);
        assertEquals(tagMetrics.size(), 1);
        assertEquals(data[0], "Java");
        assertEquals(data[1], BigInteger.valueOf(1));
    }

    @Test
    public void getArticlesBodyStreamTest() {
        Stream<String> stream = repository.getArticlesBodyStream();
        stream.forEach(body -> assertEquals(body, article.getBody()));
    }

    private Optional<Article> getAndVerify(Article article) {
        Optional<Article> optionalArticle = repository.findById(article.getId());

        assertEquals(optionalArticle.get().getId(), article.getId());
        return optionalArticle;
    }

    private Article updateArticle(String updatedTitle, Article article) {
        article.setTitle(updatedTitle);
        article.setTags(null);
        return repository.save(article);
    }

    private Article createArticle() {
        Article article = new Article();
        article.setBody(Constants.BODY);
        article.setTags(Constants.TAGS);
        article.setTitle(Constants.TITLE);
        article.setDescription(Constants.DESC);

        assertNull(article.getId());

        article = repository.save(article);
        return article;
    }
}
