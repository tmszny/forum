package com.Forum.web;

import com.Forum.Topic;
import com.Forum.data.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class HomeController {


    private TopicRepository topicRepo;

    @Autowired
    public HomeController(TopicRepository topicRepo) {
        this.topicRepo = topicRepo;
    }

    @GetMapping
    public String showHome(Model model) {
        //recent topics
        PageRequest pageTopic = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Topic> recentTopic = topicRepo.findAll(pageTopic);

        //recent post
        PageRequest pagePost = PageRequest.of(0, 10, Sort.by("lastUpdate").descending());
        Page<Topic> recentPost = topicRepo.findAll(pagePost);

        model.addAttribute("recentTopics", recentTopic);
        model.addAttribute("recentPosts", recentPost);

        return "home";
    }

    @PostMapping
    public String showHomeAfterLogin(Model model) {
        PageRequest page = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Topic> recentTopics = topicRepo.findAll(page);
        model.addAttribute("recentTopics", recentTopics);
        return "home";
    }

}
