package com.xebia.writerpad.validator;

import com.xebia.writerpad.model.ArticleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.xebia.writerpad.Constants.*;
import static com.xebia.writerpad.constant.Constants.EMPTY;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArticleValidatorTest {

    private ArticleValidator validator;

    @BeforeEach
    public void setup() {
        validator = new ArticleValidator();
    }

    @Test
    public void articleRequestValidationPassWithAllValidFields() {
        validator.validate(new ArticleRequest(TITLE, DESC, BODY, TAGS));
    }

    @Test
    public void articleRequestValidationFailsWhenTitleInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(new ArticleRequest(EMPTY, DESC, BODY, TAGS));
        });
    }

    @Test
    public void articleRequestValidationFailsWhenDescInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(new ArticleRequest(TITLE, EMPTY, BODY, TAGS));
        });
    }

    @Test
    public void articleRequestValidationFailsWhenBodyInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(new ArticleRequest(TITLE, DESC, EMPTY, TAGS));
        });
    }
}
