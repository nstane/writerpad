package com.xebia.writerpad.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class ArticlePageUtilsTest {
    @Test
    public void articlePageTest() {
        int pageNo = 1;
        int pageSize = 5;
        String title = "title";
        Assertions.assertEquals(
                ArticlePageUtils.buildPageableRequest(pageNo, pageSize, title),
                PageRequest.of(pageNo, pageSize, Sort.by(title))
        );
    }
}
