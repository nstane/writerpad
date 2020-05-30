package com.xebia.writerpad.service;

import com.xebia.writerpad.model.ArticleResults;
import com.xebia.writerpad.model.TagMetrics;
import com.xebia.writerpad.entity.Article;
import com.xebia.writerpad.exception.ArticleNotFoundException;
import com.xebia.writerpad.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.xebia.writerpad.constant.Constants.ARTICLE_NOT_FOUND_ERROR_MESSAGE;
import static com.xebia.writerpad.constant.Constants.ARTICLE_NOT_FOUND_MESSAGE;
import static com.xebia.writerpad.util.CommonUtils.isEmpty;

@Slf4j
@Service
public class ArticleService
{
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository)
    {
        this.articleRepository = articleRepository;
    }

    public Article findById(final String id)
    {
        return articleRepository.findById(id).orElseThrow(
                () -> {
                    log.error(ARTICLE_NOT_FOUND_ERROR_MESSAGE, id);
                    return new ArticleNotFoundException(ARTICLE_NOT_FOUND_MESSAGE, id);
                }
        );
    }

    public ArticleResults findAll(final Pageable pageable)
    {
        Page<Article> pageResult = articleRepository.findAll(pageable);

        return new ArticleResults(
                pageResult.getTotalElements(),
                pageResult.getContent()
        );
    }

    @Description(value = "This function will be used for save and update article")
    public Article save(Article article)
    {
        return articleRepository.save(article);
    }

    public void delete(final String id)
    {
        articleRepository.deleteById(id);
    }

    public List<TagMetrics> tagMetrics()
    {
        List<Object> records = articleRepository.findTagsMetrics();
        return records.stream().map(r -> {
            Object[] data = (Object[]) r;

            return new TagMetrics((String) data[0], ((Number) data[1]).intValue());
        }).collect(Collectors.toList());
    }

    public Article findArticleIdAndBodyById(String id)
    {
        Object[] articleData = (Object[]) articleRepository.findArticleIdAndBodyById(id);
        if (isEmpty(articleData))
        {
            log.error(ARTICLE_NOT_FOUND_ERROR_MESSAGE, id);
            throw new ArticleNotFoundException(ARTICLE_NOT_FOUND_MESSAGE, id);
        }

        Article article = new Article();
        article.setId((String) articleData[0]);
        article.setBody((String) articleData[1]);

        return article;
    }

    public Stream<String> getArticlesBodyStream()
    {
        return articleRepository.getArticlesBodyStream();
    }
}
