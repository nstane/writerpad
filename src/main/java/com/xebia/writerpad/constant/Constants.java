package com.xebia.writerpad.constant;

public final class Constants
{
    private Constants()
    {}
    public static final int ONE_HOUR = 3600;
    public static final int ONE_MINUTE_IN_SECONDS = 60;
    public static final String EMPTY = "";
    public static final String SINGLE_SPACE = " ";
    public static final String WHITE_SPACE_EXPRESSION = "\\s+";
    public static final String SPECIAL_CHAR_EXPRESSION = "\\W";
    public static final String DUPLICATE_ARTICLE_FOUND_MESSAGE = "This article has duplicate content.";
    public static final String ARTICLE_NOT_FOUND_MESSAGE = "Article with id = %s is not found.";
    public static final String ARTICLE_NOT_FOUND_ERROR_MESSAGE = "Article with id = {} is not found.";
    public static final String MISSING_TITLE_VALIDATION_MESSAGE = "Title can't be empty or null";
    public static final String MISSING_BODY_VALIDATION_MESSAGE = "Body can't be empty or null";
    public static final String MISSING_DESCRIPTION_VALIDATION_MESSAGE = "Description can't be empty or null";
}
