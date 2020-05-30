package com.xebia.writerpad.validator;

import com.xebia.writerpad.model.ArticleRequest;
import com.xebia.writerpad.exception.DuplicateArticleFoundException;
import com.xebia.writerpad.helper.DuplicateArticleContentCheckHelper;
import com.xebia.writerpad.service.ArticleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static com.xebia.writerpad.Constants.*;
import static com.xebia.writerpad.mock.helper.MockHelper.buildStringSimilarityService;
import static org.easymock.EasyMock.*;

public class DuplicateArticleContentCheckHelperTest {
    private DuplicateArticleContentCheckHelper duplicateArticleContentCheckHelper;

    @BeforeEach
    public void setup() {
        ArticleService articleService = createMock(ArticleService.class);

        expect(articleService.getArticlesBodyStream()).andReturn(Stream.of(BODY)).once();

        replay(articleService);

        duplicateArticleContentCheckHelper = new DuplicateArticleContentCheckHelper(articleService, buildStringSimilarityService());
    }


    @Test
    public void duplicateContentCheckTest() {
        Assertions.assertThrows(DuplicateArticleFoundException.class, () -> {
            duplicateArticleContentCheckHelper.checkAndValidateArticleContent(new ArticleRequest(TITLE, DESC, BODY, TAGS));
        });
    }

    @Test
    public void duplicateContentCheckWithNoDuplicateBodyTest() {
        duplicateArticleContentCheckHelper.checkAndValidateArticleContent(
                new ArticleRequest(TITLE, DESC, " test for no duplicate", TAGS)
        );
    }
}
