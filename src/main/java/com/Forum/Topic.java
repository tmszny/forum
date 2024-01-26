package com.Forum;

import com.Forum.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Column(name = "userTopic")
    private User user;
    private Date createdAt;
    private Date lastUpdate;
    private String content;

    @PrePersist
    void atCreationt() {
        this.createdAt = new Date();
    }

    public void addPost(Post post) {
        post.setTopicId(this.id);
        this.lastUpdate = new Date();
    }
}
