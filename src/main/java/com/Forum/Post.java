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
    private String user;
    private Long topicId;
    private Date createdAt;
    private String content;
    private boolean deletedByAdmin =  false;

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }

    public Post(PostDTO postDTO) {
        this.user = postDTO.getUser();
        this.content = postDTO.getContent();
        this.createdAt = postDTO.getCreatedAt();
    }

    public void deletePost() {
        this.deletedByAdmin = true;
    }

    public String getContent() {
        if (this.deletedByAdmin) {
            return "Post usunięty przez administratora";
        }
        return this.content;
    }


}
