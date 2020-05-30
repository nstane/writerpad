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
public class TagMetrics
{
    @ApiModelProperty(notes = "This field represent the name of tag.")
    private String tag;

    @ApiModelProperty(notes = "This field represent a number of tags present in all articles.")
    private int occurrence;

}
