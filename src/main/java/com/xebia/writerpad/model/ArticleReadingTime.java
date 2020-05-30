package com.xebia.writerpad.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Article read time entity")
public class ArticleReadingTime
{
    @ApiModelProperty(notes = "Represent article id.")
    private String articleId;

    @ApiModelProperty(notes = "Time to read an article.")
    private ReadTimeDetail timeToRead;
}
