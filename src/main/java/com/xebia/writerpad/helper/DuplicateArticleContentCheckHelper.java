package com.xebia.writerpad.helper;

import com.xebia.writerpad.model.ArticleRequest;
import com.xebia.writerpad.exception.DuplicateArticleFoundException;
import com.xebia.writerpad.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import net.ricecode.similarity.StringSimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.stream.Stream;

import static com.xebia.writerpad.constant.Constants.DUPLICATE_ARTICLE_FOUND_MESSAGE;

@Slf4j
@Component
public class DuplicateArticleContentCheckHelper
{
    private final ArticleService articleService;
    private final StringSimilarityService stringSimilarityService;

    @Autowired
    public DuplicateArticleContentCheckHelper(
            final ArticleService articleService,
            final StringSimilarityService stringSimilarityService
    ) {
        this.articleService = articleService;
        this.stringSimilarityService = stringSimilarityService;
    }

    @Transactional
    public void checkAndValidateArticleContent(final ArticleRequest request)
    {
        String source = request.getBody();

        boolean hasArticleDuplicateContent = false;

        try (Stream<String> contents = articleService.getArticlesBodyStream())
        {
            hasArticleDuplicateContent = contents.parallel().anyMatch(
                    target -> stringSimilarityService.score(source, target) > 0.7
            );
        } catch (Exception exception) {
            log.error("Error while checking duplicate for article : {}", request, exception);
        }

        if (hasArticleDuplicateContent)
        {
            throw new DuplicateArticleFoundException(DUPLICATE_ARTICLE_FOUND_MESSAGE);
        }
    }
}
