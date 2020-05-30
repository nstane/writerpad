package com.xebia.writerpad.service;

import com.xebia.writerpad.model.ArticleResults;
import com.xebia.writerpad.model.TagMetrics;
import com.xebia.writerpad.entity.Article;
import com.xebia.writerpad.mock.helper.MockHelper;
import com.xebia.writerpad.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.xebia.writerpad.Constants.*;
import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleServiceTest {

    private ArticleService service;

    @BeforeEach
    public void setup() {
        ArticleRepository repository = createMock(ArticleRepository.class);
        List<Article> articles = MockHelper.buildArticles();
        Article article = articles.get(0);

        expect(repository.findById(anyString())).andReturn(Optional.of(article)).once();
        expect(repository.findAll((Pageable) anyObject())).andReturn(new PageImpl(articles)).once();
        expect(repository.save(anyObject())).andReturn(Optional.of(article)).once();
        repository.deleteById(anyObject());
        expectLastCall();
        expect(repository.findTagsMetrics()).andReturn(Collections.singletonList(new Object[]{"Java", 1})).once();
        expect(repository.findArticleIdAndBodyById(anyObject())).andReturn(new Object[]{ID, BODY}).once();
        expect(repository.getArticlesBodyStream()).andReturn(Stream.of(BODY)).once();

        replay(repository);

        service = new ArticleService(repository);
    }

    @Test
    public void findArticleTest() {
        Article article = service.findById(ID);
        assertEquals(article.getId(), ID);
        assertEquals(article.getBody(), BODY);
        assertEquals(article.getDescription(), DESC);
        assertEquals(article.getTags(), TAGS);
    }

    @Test
    public void findAllArticleTest() {
        ArticleResults results = service.findAll(PageRequest.of(1, 5, Sort.by("title")));

        assertEquals(10, results.getTotal());
        assertEquals(10, results.getArticles().size());
        Article article = results.getArticles().get(0);
        assertEquals(article.getId(), ID);
        assertEquals(article.getBody(), BODY);
        assertEquals(article.getDescription(), DESC);
        assertEquals(article.getTags(), TAGS);
    }

    @Test
    public void deleteTest() {
        service.delete(ID);
    }

    @Test
    public void findArticleBodyIdTest() {
        Article article = service.findArticleIdAndBodyById(ID);
        assertEquals(article.getId(), ID);
        assertEquals(article.getBody(), BODY);
    }

    @Test
    public void tagMetricsTest() {
        List<TagMetrics> tagMetrics = service.tagMetrics();
        assertEquals(tagMetrics.size(), 1);
        assertEquals(tagMetrics.get(0).getTag(), "Java");
        assertEquals(tagMetrics.get(0).getOccurrence(), 1);
    }
}
