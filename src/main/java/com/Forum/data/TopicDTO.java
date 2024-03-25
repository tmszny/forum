package com.Forum.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TopicDTO {


    private Long id;
    private String title;
    private String user;
    private Date createdAt;
    private Date lastUpdate;
    private String content;
    private boolean open;

    public TopicDTO(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.user = topic.getUser();
        this.createdAt = topic.getCreatedAt();
        this.lastUpdate = topic.getLastUpdate();
        this.content = topic.getContent();
        this.open = topic.isOpen();
    }
}
