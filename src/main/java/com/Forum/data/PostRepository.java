package com.Forum.data;

import com.Forum.Post;
import com.Forum.Topic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface PostRepository extends CrudRepository<Post, Long> {
    ArrayList<Post> findAllPostsByTopicId(Long topicId);

    @Query("select p from Post p where p.content like %:#{#content}%")
    ArrayList<Post> searchPost(@Param("content") String content);

    ArrayList<Post> findAllPostsByUser(String username);
}
