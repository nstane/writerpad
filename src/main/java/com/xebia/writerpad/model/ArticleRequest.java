package com.xebia.writerpad.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static com.xebia.writerpad.util.CommonUtils.isEmpty;
import static com.xebia.writerpad.util.CommonUtils.isNotBlank;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest
{
    private String title;

    private String description;

    private String body;

    private Set<String> tags;

    public void setTags(Set<String> tags)
    {
        if (isEmpty(tags))
        {
            return;
        }
        this.tags = tags.stream().filter(t -> isNotBlank(t)).map(t -> t.trim().toLowerCase(Locale.US)).collect(Collectors.toSet());
    }

    public void setTitle(String title)
    {
        if (isNotBlank(title))
        {
            this.title = title.trim();
        }
    }

    public void setBody(String body)
    {
        if (isNotBlank(body))
        {
            this.body = body.trim();
        }
    }

    public void setDescription(String description)
    {
        if (isNotBlank(description))
        {
            this.description = description.trim();
        }
    }
}

