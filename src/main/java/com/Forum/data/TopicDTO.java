package com.Forum.data;

import com.Forum.Post;
import com.Forum.Topic;
import com.Forum.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TopicDTO {

    private String title;
    private User user;
    private Date createdAt;
    private Date lastUpdate;
    private String content;
    private List<Post> thread;

    private TopicDTO(Topic topic) {
        this.title = topic.getTitle();
        this.user = topic.getUser();
        this.createdAt = topic.getCreatedAt();
        this.lastUpdate = topic.getLastUpdate();
        this.content = topic.getContent();
    }

    public TopicDTO topicToDTO(Topic topic) {
        return new TopicDTO(topic);
    }
}
