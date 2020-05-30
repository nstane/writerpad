package com.xebia.writerpad.helper;

import com.xebia.writerpad.model.ArticleReadingTime;
import com.xebia.writerpad.model.ReadTimeDetail;
import com.xebia.writerpad.entity.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeToReadHelperTest {
    private TimeToReadHelper timeToReadHelper;

    @BeforeEach
    public void setup() {
        timeToReadHelper = new TimeToReadHelper();
        ReflectionTestUtils.setField(timeToReadHelper, "averageHumanReadTime", 200);
    }

    @Test
    public void timeToReadData() {
        final String id = "4028ab3c722e0bb701722e0eb34c0001";
        ArticleReadingTime articleReadingTime = timeToReadHelper.getTimeToRead(buildArticleWithId(id));
        ReadTimeDetail readTimeDetail = articleReadingTime.getTimeToRead();
        assertEquals(articleReadingTime.getArticleId(), id);
        assertEquals(readTimeDetail.getMins(), 0);
        assertEquals(readTimeDetail.getSeconds(), 31);

    }

    private Article buildArticleWithId(String id) {
        Article article = new Article();
        article.setId(id);
        article.setBody("You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. You have to believe. ");
        return article;
    }
}
