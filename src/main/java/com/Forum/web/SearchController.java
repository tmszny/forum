package com.Forum.web;

import com.Forum.data.Topic;
import com.Forum.data.PostRepository;
import com.Forum.data.TopicDTO;
import com.Forum.data.TopicRepository;
import com.Forum.services.TopicService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping
public class SearchController {

    private TopicService topicService;

    public SearchController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/search")
    public String search(@ModelAttribute("searchValue") String searchedPhrase, Model model) {
        Page<TopicDTO> searchResult = topicService.searchForum(searchedPhrase);
        model.addAttribute("searchedTopics", searchResult);

        return "searchResult";
    }
}
