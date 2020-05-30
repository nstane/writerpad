package com.xebia.writerpad.exception;

public class ArticleNotFoundException extends RuntimeException
{
    private final String articleId;

    public ArticleNotFoundException(String message, String articleId)
    {
        super(message);
        this.articleId = articleId;
    }

    @Override
    public String getMessage()
    {
        return String.format(super.getMessage(), articleId);
    }
}
