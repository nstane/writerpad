package com.xebia.writerpad.exception;

public class DuplicateArticleFoundException extends RuntimeException
{
    public DuplicateArticleFoundException(String message)
    {
        super(message);
    }

    @Override
    public String getMessage()
    {
        return super.getMessage();
    }
}
