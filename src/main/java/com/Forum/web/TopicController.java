package com.Forum.web;

import com.Forum.data.*;
import com.Forum.services.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping
public class TopicController {

    private TopicService topicService;
    @ModelAttribute("topic")
    public TopicDTO topic() {
        return new TopicDTO();
    }

    @ModelAttribute("post")
    public PostDTO post() {
        return new PostDTO();
    }

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/new")
    public String newTopic() {
        return "newTopic";
    }

    @PostMapping("/new")
    public String topicCreation(@ModelAttribute("topic") TopicDTO topicDTO, HttpServletRequest httpServletRequest) {
        String currentUsername = httpServletRequest.getUserPrincipal().getName();
        TopicDTO createdTopic = topicService.createTopic(topicDTO, currentUsername);
        return "redirect:/topicView/" + createdTopic.getId();
    }

    @GetMapping("/topicView/{id}")
    public String showTopic(@PathVariable("id") Long id, Model model) {
        topicService.showTopicById(id, model);
        if (topicService.isTopicOpen(id)) {
            model.addAttribute("post", post());
        }

        return "topicView";
    }

    @PostMapping("/topicView/{id}")
    public String addPost(@PathVariable("id") Long id, @ModelAttribute("post") PostDTO post, HttpServletRequest httpServletRequest) {
        topicService.addPostToTopic(id, post, httpServletRequest);
        return "redirect:/topicView/" + id;
    }

    @PostMapping("/topicView/close/{id}")
    public String closeTopic(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        topicService.closeTopic(id, httpServletRequest);
        return "redirect:/topicView/" + id;
    }

    @PostMapping("/topicView/close/{TopicId}/{PostId}")
    public String deletePost(@PathVariable("TopicId") Long topicId, @PathVariable("PostId") Long postId, HttpServletRequest httpServletRequest) {
        topicService.deletePostFromTopic(topicId, postId, httpServletRequest);
        return "redirect:/topicView/" + topicId;
    }
}
