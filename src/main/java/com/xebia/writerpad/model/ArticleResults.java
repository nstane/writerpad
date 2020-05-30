package com.xebia.writerpad.model;

import com.xebia.writerpad.entity.Article;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleResults
{
    private long total;
    private List<Article> articles;
}
