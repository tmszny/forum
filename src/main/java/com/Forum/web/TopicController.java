package com.Forum.web;

import com.Forum.Post;
import com.Forum.Topic;
import com.Forum.User;
import com.Forum.data.PostDTO;
import com.Forum.data.PostRepository;
import com.Forum.data.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping
public class TopicController {

    private TopicRepository topicRepo;
    private PostRepository postRepo;

    @ModelAttribute("topic")
    public Topic topic() {return new Topic();}

    @ModelAttribute("post")
    public PostDTO post() {return new PostDTO();}

    @Autowired
    public TopicController(TopicRepository topicRepo, PostRepository postRepo) {
        this.topicRepo = topicRepo;
        this.postRepo = postRepo;
    }

    @GetMapping("/new")
    public String newTopic() {
        return "newTopic";
    }

    @PostMapping("/new")
    public String topicCreation(@ModelAttribute("topic") Topic topic, @AuthenticationPrincipal User user) {
        topic.setUser(user);
        topicRepo.save(topic);
        return "redirect:/topicView/" + topic.getId();
    }

    @GetMapping("/topicView/{id}")
    public String showTopic(@PathVariable("id") Long id, Model model) {
        Optional<Topic> optTopic = topicRepo.findById(id);
        if (optTopic.isPresent()) {
            Topic actuallTopic = optTopic.get();
            ArrayList<Post> postsInTopic = postRepo.findAllPostsByTopicId(id);
            model.addAttribute("topicById", actuallTopic);
            model.addAttribute("post", post());
            model.addAttribute("postsInTopic", postsInTopic);
            return "topicView";
        }
        return "topicView"; // TODO obsługa nieistniejących tematów
    }

    @PostMapping("/topicView/{id}")
    public String addPost(@PathVariable("id") Long id, @ModelAttribute("post") PostDTO post, @AuthenticationPrincipal User user) {
        Optional<Topic> optTopic = topicRepo.findById(id);

        if (optTopic.isPresent()) {
            Topic actuallTopic = optTopic.get();
            Post newPost = new Post(post);
            newPost.setUser(user);
            actuallTopic.addPost(newPost);
            postRepo.save(newPost);
            return "redirect:/topicView/" + id;
        }
        return "/"; // TODO obsługa nieistniejących tematów
    }
}
