package com.xebia.writerpad.mock.helper;

import com.xebia.writerpad.model.*;
import com.xebia.writerpad.entity.Article;
import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.xebia.writerpad.Constants.*;

public class MockHelper {

    public static List<Article> buildArticles() {

        return IntStream.rangeClosed(1, 10).mapToObj(id -> buildArticle(String.valueOf(id))).collect(Collectors.toList());
    }

    public static Article buildArticle(String id) {
        return new Article(id, "dummy-slug", TITLE, DESC, BODY, TAGS, new Date(), new Date(), false, 0);
    }

    public static ArticleReadingTime buildArticleReadTime() {
        return new ArticleReadingTime(String.valueOf(1),
                new ReadTimeDetail(0, 1)
        );
    }

    public static List<TagMetrics> buildTagMetrics() {
        return Collections.singletonList(new TagMetrics("Java", 10));
    }

    public static ArticleRequest buildArticleRequest(String body) {
        return new ArticleRequest(TITLE, DESC, body, null);
    }

    public static ArticleRequest buildArticleRequest() {
        return new ArticleRequest(TITLE, DESC, BODY, TAGS);
    }

    public static ArticleRequest buildEmptyArticleRequest() {
        return new ArticleRequest(null, null, null, null);
    }

    public static StringSimilarityService buildStringSimilarityService() {
        SimilarityStrategy strategy = new JaroWinklerStrategy();

        return new StringSimilarityServiceImpl(strategy);
    }
}
