package com.xebia.writerpad.validator;

import com.xebia.writerpad.model.ArticleRequest;
import org.springframework.stereotype.Component;

import static com.xebia.writerpad.constant.Constants.*;
import static com.xebia.writerpad.util.CommonUtils.isBlank;

@Component
public class ArticleValidator implements Validator<ArticleRequest>
{
    public void validate(ArticleRequest articleRequest)
    {
        if (isBlank(articleRequest.getTitle()))
        {
            throw new IllegalArgumentException(MISSING_TITLE_VALIDATION_MESSAGE);
        }

        if (isBlank(articleRequest.getBody()))
        {
            throw new IllegalArgumentException(MISSING_BODY_VALIDATION_MESSAGE);
        }

        if (isBlank(articleRequest.getDescription()))
        {
            throw new IllegalArgumentException(MISSING_DESCRIPTION_VALIDATION_MESSAGE);
        }
    }
}
