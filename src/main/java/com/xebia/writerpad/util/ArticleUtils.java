package com.xebia.writerpad.util;

import com.xebia.writerpad.model.ArticleRequest;
import com.xebia.writerpad.entity.Article;
import org.springframework.web.bind.annotation.RequestBody;

import static com.xebia.writerpad.util.CommonUtils.isNotBlank;
import static com.xebia.writerpad.util.CommonUtils.isNotEmpty;

public class ArticleUtils
{
    public static boolean isArticleUpdatable(@RequestBody ArticleRequest request, Article article)
    {
        boolean isUpdatable = false;
        if (isNotBlank(request.getTitle()))
        {
            article.setTitle(request.getTitle());
            isUpdatable = true;
        }

        if (isNotBlank(request.getTitle()))
        {
            article.setTitle(request.getTitle());
            article.setSlug();
            isUpdatable = true;
        }

        if (isNotBlank(request.getBody()))
        {
            article.setBody(request.getBody());
            isUpdatable = true;
        }

        if (isNotBlank(request.getDescription()))
        {
            article.setDescription(request.getDescription());
            isUpdatable = true;
        }

        if (isNotEmpty(request.getTags()))
        {
            article.getTags().addAll(request.getTags());
            isUpdatable = true;
        }

        return isUpdatable;
    }

    public static Article buildArticle(@RequestBody ArticleRequest request)
    {
        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setBody(request.getBody());
        article.setDescription(request.getDescription());
        article.setTags(request.getTags());
        article.setSlug();
        return article;
    }
}
