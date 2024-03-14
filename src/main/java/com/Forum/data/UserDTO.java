package com.Forum.data;

import lombok.Data;

@Data
public class UserDTO {

    private final String username;
    private final String email;

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
