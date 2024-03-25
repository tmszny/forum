package com.Forum.services;

import com.Forum.data.User;
import com.Forum.data.UserDTO;
import com.Forum.data.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUser(String username) throws ChangeSetPersister.NotFoundException {

        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isPresent()) {
            User user = optUser.get();
            String mail = user.getEmail();
            return  new UserDTO(username, mail);
        }
        throw new ChangeSetPersister.NotFoundException();
    }
}
