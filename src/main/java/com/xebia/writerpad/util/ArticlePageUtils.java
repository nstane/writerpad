package com.xebia.writerpad.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ArticlePageUtils
{
    public static Pageable buildPageableRequest(final Integer pageNo, final Integer pageSize, final String sortBy)
    {
        return PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    }
}
