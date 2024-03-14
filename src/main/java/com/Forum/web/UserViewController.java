package com.Forum.web;

import com.Forum.Topic;
import com.Forum.TopicService;
import com.Forum.UserService;
import com.Forum.data.UserDTO;
import com.Forum.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/user")
public class UserViewController {

    private UserService userService;
    private TopicService topicService;

    public UserViewController(UserService userService, TopicService topicService) {
        this.userService = userService;
        this.topicService = topicService;
    }

    @GetMapping("/{username}")
    public String showUserPage(@PathVariable("username") String username, Model model) throws ChangeSetPersister.NotFoundException {

        UserDTO userDetails = userService.getUser(username);
        ArrayList<Topic> userTopics = topicService.getTopicsByUser(username);
        ArrayList<Topic> userRespondTopics = topicService.getTopicsByUsersPost(username);

        model
                .addAttribute("userDetails", userDetails)
                .addAttribute("userTopics", userTopics)
                .addAttribute("userRespondTopics", userRespondTopics);

        return "userPage";
    }
}
