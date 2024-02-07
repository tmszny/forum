package com.Forum.web;

import com.Forum.Post;
import com.Forum.Topic;
import com.Forum.User;
import com.Forum.data.PostDTO;
import com.Forum.data.PostRepository;
import com.Forum.data.TopicRepository;
import com.Forum.data.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping
public class TopicController {

    private TopicRepository topicRepo;
    private PostRepository postRepo;
    private UserRepository userRepo;

    @ModelAttribute("topic")
    public Topic topic() {return new Topic();}

    @ModelAttribute("post")
    public PostDTO post() {return new PostDTO();}

    @Autowired
    public TopicController(TopicRepository topicRepo, PostRepository postRepo, UserRepository userRepo) {
        this.topicRepo = topicRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/new")
    public String newTopic() {
        return "newTopic";
    }

    @PostMapping("/new")
    public String topicCreation(@ModelAttribute("topic") Topic topic, HttpServletRequest httpServletRequest) {
        String currentPrincipalName = httpServletRequest.getUserPrincipal().getName();
        topic.setUser(currentPrincipalName);
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
            model.addAttribute("postsInTopic", postsInTopic);

            if (actuallTopic.isOpen()) {
                model.addAttribute("post", post());
            }
            return "topicView";
        }
        return "topicView"; // TODO obsługa nieistniejących tematów
    }

    @PostMapping("/topicView/{id}")
    public String addPost(@PathVariable("id") Long id, @ModelAttribute("post") PostDTO post, HttpServletRequest httpServletRequest) {
        Optional<Topic> optTopic = topicRepo.findById(id);

        if (optTopic.isPresent()) {
            Topic actuallTopic = optTopic.get();
            Post newPost = new Post(post);
            String currentPrincipalName = httpServletRequest.getUserPrincipal().getName();
            newPost.setUser(currentPrincipalName);
            actuallTopic.addPost(newPost);
            postRepo.save(newPost);
            return "redirect:/topicView/" + id;
        }
        return "/"; // TODO obsługa nieistniejących tematów
    }

    @PostMapping("/topicView/close/{id}")
    public String closeTopic(@PathVariable("id") Long id) {
        log.info("Jestem tu");
        Topic topic = topicRepo.findById(id).get();
        topic.closeTopic();
        topicRepo.save(topic);

        return "redirect:/topicView/" + id;
    }

    @PostMapping("/topicView/close/{TopicId}/{PostId}")
    public String deletePost(@PathVariable("TopicId") Long topicId, @PathVariable("PostId") Long postId, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.isUserInRole("ROLE_ADMIN")) {
            Optional<Post> optPost = postRepo.findById(postId);

            if (optPost.isPresent()) {
                Post deletedPost =  optPost.get();
                deletedPost.deletePost();
                postRepo.save(deletedPost);
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        return "redirect:/topicView/" + topicId;
    }
}
