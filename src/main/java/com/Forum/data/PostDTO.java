package com.Forum.data;

import lombok.Data;

import java.util.Date;

@Data
public class PostDTO {
    private String user;
    private Date createdAt;
    private String content;

}
