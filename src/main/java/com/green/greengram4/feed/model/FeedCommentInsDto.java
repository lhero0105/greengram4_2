package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FeedCommentInsDto {
    @JsonIgnore
    private int ifeedComment;
    @JsonIgnore
    private int iuser;
    @Min(1)
    private int ifeed;
    // @Range Integer 타입
    @NotEmpty // null 이거나 비어있을 떄
    @Size(min = 3)
    private String comment;
}

