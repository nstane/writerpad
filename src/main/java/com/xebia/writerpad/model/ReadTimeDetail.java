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
@ApiModel(description = "Tag metrics entity")
public class ReadTimeDetail
{
    @ApiModelProperty(notes = "Article read time in minutes.")
    private int mins;

    @ApiModelProperty(notes = "Article read time in seconds.")
    private int seconds;
}
