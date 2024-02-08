package com.Forum.web;

import com.Forum.Topic;
import com.Forum.data.PostRepository;
import com.Forum.data.TopicRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping
public class SearchController {

    private TopicRepository topicRepo;
    private PostRepository postRepo;

    public SearchController(TopicRepository topicRepo, PostRepository postRepo) {
        this.postRepo = postRepo;
        this.topicRepo = topicRepo;
    }

    @PostMapping("/search")
    public String search(@ModelAttribute("searchValue") String searchedPhrase, Model model) {
        HashMap<Long, Topic> resultMap = new HashMap<>();

        topicRepo.searchTopicsTitle(searchedPhrase).forEach(topic ->
                resultMap.put(topic.getId(), topic));

        topicRepo.searchTopicsContent(searchedPhrase).forEach(topic ->
                resultMap.put(topic.getId(), topic));

        postRepo.searchPost(searchedPhrase).forEach(post -> {
            Topic postOwner = topicRepo.findById(post.getTopicId()).get();
            resultMap.put(postOwner.getId(), postOwner);
        });

        model.addAttribute("searchedTopics", resultMap);

        return "searchResult";
    }
}
