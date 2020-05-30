package com.xebia.writerpad.controller;

import com.xebia.writerpad.model.*;
import com.xebia.writerpad.entity.Article;
import com.xebia.writerpad.exception.DuplicateArticleFoundException;
import com.xebia.writerpad.helper.DuplicateArticleContentCheckHelper;
import com.xebia.writerpad.helper.TimeToReadHelper;
import com.xebia.writerpad.service.ArticleService;
import com.xebia.writerpad.validator.ArticleValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.xebia.writerpad.Constants.*;
import static com.xebia.writerpad.mock.helper.MockHelper.*;
import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArticleControllerTest {

    private ArticleController controller;

    @BeforeEach
    public void setup() {
        ArticleService articleService = buildAndReplayArticleService();

        DuplicateArticleContentCheckHelper duplicateContentCheckHelper = new DuplicateArticleContentCheckHelper(
                articleService,
                buildStringSimilarityService()
        );

        TimeToReadHelper timeToReadHelper = buildAndReplayTimeToReadHelper();

        ArticleValidator articleValidator = new ArticleValidator();

        controller = new ArticleController(articleValidator, articleService, timeToReadHelper, duplicateContentCheckHelper);
    }

    @Test
    public void findAllTest() {
        ArticleResults articles = controller.findAll(1, 10, "title");
        assertEquals(articles.getArticles().size(), 10);
    }

    @Test
    public void findByIdTest() {
        String id = String.valueOf(1);
        Article article = controller.find(id);
        Set<String> tags = article.getTags();
        assertEquals(article.getId(), id);
        assertEquals(article.getSlug(), "dummy-slug");
        assertEquals(tags.size(), 1);
        tags.stream().forEach(t-> assertEquals("Java", t));
    }

    @Test
    public void findTagMetrics() {
        List<TagMetrics> tagMetrics = controller.tagMetrics();
        assertEquals(tagMetrics.size(), 1);
        assertEquals(tagMetrics.get(0).getTag(), "Java");
        assertEquals(tagMetrics.get(0).getOccurrence(), 10);
    }

    @Test
    public void timeToReadTest() {
        ArticleReadingTime readTime = controller.timeToRead(ID);
        ReadTimeDetail readingTime = readTime.getTimeToRead();
        assertEquals(readTime.getArticleId(), ID);
        assertEquals(readingTime.getMins(), 0);
        assertEquals(readingTime.getSeconds(), 1);
    }

    @Test
    public void updateTest() {
        controller.update(buildArticleRequest("Dummy Body to test update"), ID);
    }

    @Test
    public void updateWithEmptyBodyTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.update(buildEmptyArticleRequest(), ID);
        });
    }

    @Test
    public void updateWithDuplicateBodyTest() {
        assertThrows(DuplicateArticleFoundException.class, () -> {
            controller.update(buildArticleRequest(), ID);
        });
    }

    @Test
    public void addDuplicateArticleTest() {
        assertThrows(DuplicateArticleFoundException.class, () -> {
            controller.addArticle(buildArticleRequest());
        });
    }

    @Test
    public void addEmptyArticleTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.addArticle(buildEmptyArticleRequest());
        });
    }

    @Test
    @Description("Add article with body null")
    public void addArticleWithEmptyRequiredFieldTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.addArticle(buildArticleRequest(null));
        });
    }

    @Test
    @Description("Add article with body null")
    public void addArticleTest() {
        ResponseEntity<Article> responseEntity = controller.addArticle(buildArticleRequest(DUMMY_BODY));
        Article article = responseEntity.getBody();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertEquals(article.getBody(), DUMMY_BODY);
    }

    private TimeToReadHelper buildAndReplayTimeToReadHelper() {
        TimeToReadHelper timeToReadHelper = createMock(TimeToReadHelper.class);
        expect(timeToReadHelper.getTimeToRead(anyObject())).andReturn(buildArticleReadTime()).once();
        replay(timeToReadHelper);
        return timeToReadHelper;
    }

    private ArticleService buildAndReplayArticleService() {
        ArticleService articleService = createMock(ArticleService.class);
        List<Article> articles = buildArticles();
        ArticleResults articleResults = new ArticleResults();
        articleResults.setArticles(articles);

        Article article = articles.get(0);

        expect(articleService.findAll(anyObject())).andReturn(articleResults).once();
        expect(articleService.findById(anyString())).andReturn(article).once();
        expect(articleService.findArticleIdAndBodyById(anyString())).andReturn(article).once();
        expect(articleService.tagMetrics()).andReturn(buildTagMetrics()).once();
        article.setBody(DUMMY_BODY);
        expect(articleService.save(anyObject())).andReturn(article).once();
        expect(articleService.getArticlesBodyStream()).andReturn(Stream.of(BODY)).anyTimes();
        replay(articleService);
        return articleService;
    }
}
