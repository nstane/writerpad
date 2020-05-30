package com.xebia.writerpad.util;

import com.xebia.writerpad.Constants;
import com.xebia.writerpad.model.ArticleRequest;
import com.xebia.writerpad.entity.Article;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static com.xebia.writerpad.mock.helper.MockHelper.buildArticleRequest;
import static com.xebia.writerpad.mock.helper.MockHelper.buildEmptyArticleRequest;
import static com.xebia.writerpad.util.ArticleUtils.isArticleUpdatable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleUtilsTest {
    @Test
    public void articleUpdatableTest() {
        Article article = new Article();
        article.setTags(new HashSet<>());
        ArticleRequest request = buildArticleRequest();
        assertEquals(isArticleUpdatable(request, article), true);

        verifyArticle(article, Constants.BODY, Constants.TITLE, Constants.DESC, 1);

    }

    @Test
    public void articleNotUpdatableTest() {
        Article article = new Article();
        article.setTags(new HashSet<>());
        ArticleRequest request = buildEmptyArticleRequest();

        assertEquals(isArticleUpdatable(request, article), false);

        verifyArticle(article, null, null, null, 0);

    }

    @Test
    public void buildArticleTest() {
        Article article = ArticleUtils.buildArticle(buildArticleRequest());
        verifyArticle(article, Constants.BODY, Constants.TITLE, Constants.DESC, 1);
    }


    private void verifyArticle(Article article, String body, String title, String desc, int i) {
        assertEquals(article.getBody(), body);
        assertEquals(article.getTitle(), title);
        assertEquals(article.getDescription(), desc);
        assertEquals(article.getTags().size(), i);
    }
}
