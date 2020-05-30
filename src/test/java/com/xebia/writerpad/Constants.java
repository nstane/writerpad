package com.xebia.writerpad;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Constants {
    public static final String TITLE = "Learn Java 8 features";
    public static final String DESC = "Description Learn Java 8 features";
    public static final String BODY = "Article BOdy with text 'Learn Java 8 features'";
    public static final Set<String> TAGS = Collections.singleton("Java");

    public static final String ARTICLE_API = "/api/articles";
    public static final String ARTICLE_API_WITH_ID = "/api/articles/{slug-uuid}";
    public static final String TAGS_OCCURRENCE_API = "/api/articles/tags";
    public static final String TIME_TO_READ_API = "/api/articles/{slug-uuid}/timetoread";
    public static final String DUMMY_BODY = "dummy-body";
    public static final String ID = String.valueOf(1);
}
