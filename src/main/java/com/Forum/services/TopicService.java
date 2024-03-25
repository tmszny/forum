package com.Forum.services;

import com.Forum.data.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

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

    public Page<TopicDTO> showRecentTopics() {

        PageRequest page = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Topic> recentTopics = topicRepository.findAll(page);

        return recentTopics.map(topic -> new TopicDTO(topic));
    }

    public Page<TopicDTO> showRecentDiscussion() {

        PageRequest pagePost = PageRequest.of(0, 10, Sort.by("lastUpdate").descending());
        Page<Topic> recentPost = topicRepository.findAll(pagePost);

        return recentPost.map(topic -> new TopicDTO(topic));
    }

    public Page<TopicDTO> searchForum(String searchedPhrase) {
        HashMap<Long, Topic> resultMap = new HashMap<>();

        topicRepository.searchTopicsTitle(searchedPhrase).forEach(topic ->
                resultMap.put(topic.getId(), topic));

        topicRepository.searchTopicsContent(searchedPhrase).forEach(topic ->
                resultMap.put(topic.getId(), topic));

        postRepository.searchPost(searchedPhrase).forEach(post -> {
            Topic postOwner = topicRepository.findById(post.getTopicId()).get();
            resultMap.put(postOwner.getId(), postOwner);
        });

        List<TopicDTO> resultArray = new ArrayList<>();
        resultMap.values().forEach(result -> resultArray.add(new TopicDTO(result)));
        PageRequest pagePost = PageRequest.of(0, 10, Sort.by("lastUpdate").descending());

        return new PageImpl<>(resultArray, pagePost, 100);
    }

    public TopicDTO createTopic(TopicDTO topicDTO, String username) {
        Topic createdTopic = new Topic(topicDTO);
        createdTopic.setUser(username);
        topicRepository.save(createdTopic);
        return new TopicDTO(createdTopic);
    }

    public void closeTopic(Long id, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.isUserInRole("ROLE_ADMIN")) {
            Topic topic = topicRepository.findById(id).get();
            topic.closeTopic();
            topicRepository.save(topic);
        }
    }

    public void addPostToTopic(Long id, PostDTO postDTO, HttpServletRequest httpServletRequest) {
        Optional<Topic> optTopic = topicRepository.findById(id);

        if (optTopic.isPresent()) {
            Topic actualTopic = optTopic.get();
            Post newPost = new Post(postDTO);
            String currentPrincipalName = httpServletRequest.getUserPrincipal().getName();
            newPost.setUser(currentPrincipalName);
            actualTopic.addPost(newPost);
            postRepository.save(newPost);
        }
    }

    public void showTopicById(Long id, Model model) {
        Optional<Topic> optTopic = topicRepository.findById(id);
        if (optTopic.isPresent()) {
            Topic actualTopic = optTopic.get();
            ArrayList<Post> postsInTopic = postRepository.findAllPostsByTopicId(id);
            model.addAttribute("topicById", actualTopic);
            model.addAttribute("postsInTopic", postsInTopic);
        }
    }

    public boolean isTopicOpen(Long id) {
        Optional<Topic> optTopic = topicRepository.findById(id);
        if (optTopic.isPresent()) {
            return optTopic.get().isOpen();
        }
        return false;
    }

    public void deletePostFromTopic(Long topicId, Long postId, HttpServletRequest httpServletRequest) {

        if (httpServletRequest.isUserInRole("ROLE_ADMIN")) {
            Optional<Post> optPost = postRepository.findById(postId);

            if (optPost.isPresent()) {
                Post deletedPost =  optPost.get();
                deletedPost.deletePost();
                postRepository.save(deletedPost);
            }
        }
    }
}
