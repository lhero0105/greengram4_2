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
    @Min(value = 1, message = "메세지를 1개이상 입력해주")
    private int ifeed;
    // @Range Integer 타입
    @NotEmpty // null 이거나 비어있을 떄
    @Size(min = 3)
    private String comment;
}

