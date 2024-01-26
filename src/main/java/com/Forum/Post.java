package com.Forum;

import com.Forum.data.PostDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "userPost")
    private User user;
    private Long topicId;
    private Date createdAt;
    private String content;

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }

    public Post(PostDTO postDTO) {
        this.user = postDTO.getUser();
        this.content = postDTO.getContent();
        this.createdAt = postDTO.getCreatedAt();
    }



}
