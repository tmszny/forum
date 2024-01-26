package com.Forum.data;

import com.Forum.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PostRepository extends CrudRepository<Post, Long> {
    ArrayList<Post> findAllPostsByTopicId(Long topicId);
}
