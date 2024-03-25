package com.Forum.data;

import com.Forum.data.Post;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @Column(name = "userTopic")
    private String user;

    private Date createdAt;
    private Date lastUpdate;

    @Lob
    @Column(name = "content", columnDefinition="CLOB")
    private String content;
    private boolean open;

    @PrePersist
    void atCreation() {
        this.createdAt = new Date();
        this.open = true;
    }

    public void addPost(Post post) {
        post.setTopicId(this.id);
        this.lastUpdate = new Date();
    }

    public void closeTopic() {
        this.open = false;
    }

    public Topic(TopicDTO topicDTO) {
        this.title = topicDTO.getTitle();
        this.content = topicDTO.getContent();
    }
}
