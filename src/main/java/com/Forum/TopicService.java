package com.Forum;

import com.Forum.data.PostRepository;
import com.Forum.data.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class TopicService {

    private TopicRepository topicRepository;
    private PostRepository postRepository;

    public TopicService(TopicRepository topicRepository, PostRepository postRepository) {
        this.topicRepository = topicRepository;
        this.postRepository = postRepository;
    }

    public ArrayList<Topic> getTopicsByUser(String username) {
        return topicRepository.findAllTopicsByUser(username);
    }

    public ArrayList<Topic> getTopicsByUsersPost(String username) {

        HashSet<Long> userActiveTopicsById = new HashSet<>();
        postRepository.findAllPostsByUser(username)
                .forEach(post ->
                        userActiveTopicsById.add(post.getTopicId()));

        ArrayList<Topic> result = new ArrayList<>();
        userActiveTopicsById
                .forEach(topicID -> {
                    Topic topic = topicRepository.findById(topicID).get();
                    if (!topic.getUser().equals(username)) {
                        result.add(topic);
                    }
                });

        return result;
    }
}
