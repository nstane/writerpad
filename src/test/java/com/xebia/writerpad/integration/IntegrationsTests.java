package com.xebia.writerpad.integration;

import com.xebia.writerpad.model.ArticleReadingTime;
import com.xebia.writerpad.model.ArticleRequest;
import com.xebia.writerpad.model.ReadTimeDetail;
import com.xebia.writerpad.entity.Article;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static com.xebia.writerpad.Constants.*;
import static com.xebia.writerpad.constant.Constants.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "com.xebia.writerpad")
public class IntegrationsTests {
    @Autowired
    protected TestRestTemplate restTemplate;

    private ResponseEntity<Article> postResponse;

    @BeforeEach
    public void setup() {
        postResponse = restTemplate.postForEntity(ARTICLE_API, buildArticleRequest(TITLE), Article.class);
    }

    @AfterEach
    public void destory() {
        restTemplate.delete(ARTICLE_API_WITH_ID, postResponse.getBody().getId());
    }

    @Test
    public void articlePostAPITest() {

        assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ARTICLE_API, buildArticleRequest(EMPTY), String.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void articlePatchAPITest() {
        assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED);

        String id = postResponse.getBody().getId();
        String titleAfterUpdate = TITLE + "Patch";

        // updating only title

        /*ResponseEntity<Article> articleResponseEntity = restTemplate.patchForObject(
                ARTICLE_API_WITH_ID,
                buildArticleRequest(titleAfterUpdate),
                ResponseEntity.class,
                id
        );

        assertEquals(articleResponseEntity.getBody().getTitle(), titleAfterUpdate);*/

    }

    @Test
    public void articleGetAPITest() {
        assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED);

        String id = postResponse.getBody().getId();

        ResponseEntity<Article> articleResponse = restTemplate.getForEntity(ARTICLE_API_WITH_ID, Article.class, id);

        assertEquals(articleResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(articleResponse.getBody().getId(), id);
    }

    @Test
    public void articleDeleteAPITest() {
        assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED);

        String id = postResponse.getBody().getId();

        restTemplate.delete(ARTICLE_API_WITH_ID, id);

        ResponseEntity<String> articleResponse = restTemplate.getForEntity(ARTICLE_API_WITH_ID, String.class, id);

        assertEquals(articleResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void articleTimeToReadAPITest() {
        assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED);
        String id = postResponse.getBody().getId();

        ResponseEntity<ArticleReadingTime> responseEntity = restTemplate.getForEntity(TIME_TO_READ_API, ArticleReadingTime.class, id);

        ArticleReadingTime articleReadingTime = responseEntity.getBody();
        ReadTimeDetail readTimeDetail = articleReadingTime.getTimeToRead();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(articleReadingTime.getArticleId(), id);
        assertEquals(readTimeDetail.getMins(), 0);
        assertEquals(readTimeDetail.getSeconds(), 2);
    }

    @Test
    public void tagMetricsAPITest() {
        assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED);

        ResponseEntity<List> tagMetricsResponse = restTemplate.getForEntity(TAGS_OCCURRENCE_API, List.class);

        assertEquals(tagMetricsResponse.getStatusCode(), HttpStatus.OK);

        List<Map> tagMetrics = tagMetricsResponse.getBody();
        tagMetrics.forEach(tm -> {
            Map map = tm;
            assertEquals(map.get("occurrence"), 1);
            TAGS.stream().forEach(t-> assertEquals(map.get("tag"), t.toLowerCase()));

        });
    }

    private ArticleRequest buildArticleRequest(String title) {
        return new ArticleRequest(title, DESC, BODY, TAGS);
    }
}
