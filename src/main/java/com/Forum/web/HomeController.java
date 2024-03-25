package com.Forum.web;

import com.Forum.data.Topic;
import com.Forum.data.TopicDTO;
import com.Forum.data.TopicRepository;
import com.Forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {


    private TopicService topicService;

    @Autowired
    public HomeController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public String showHome(Model model) {
        //recent topics
        Page<TopicDTO> recentTopic = topicService.showRecentTopics();
        //recent post
        Page<TopicDTO> recentPost = topicService.showRecentDiscussion();

        model.addAttribute("recentTopics", recentTopic);
        model.addAttribute("recentPosts", recentPost);
        return "home";
    }

    @PostMapping
    public String showHomeAfterLogin(Model model) {
        //recent topics
        Page<TopicDTO> recentTopic = topicService.showRecentTopics();
        //recent post
        Page<TopicDTO> recentPost = topicService.showRecentDiscussion();

        model.addAttribute("recentTopics", recentTopic);
        model.addAttribute("recentPosts", recentPost);
        return "home";
    }

}
