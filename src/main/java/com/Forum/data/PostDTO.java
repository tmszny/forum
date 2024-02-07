package com.Forum.data;

import com.Forum.Post;
import com.Forum.Topic;
import com.Forum.User;
import lombok.Data;

import java.util.Date;

@Data
public class PostDTO {
    private String user;
    private Date createdAt;
    private String content;

}
