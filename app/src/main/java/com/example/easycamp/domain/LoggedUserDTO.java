package com.example.easycamp.domain;

public class LoggedUserDTO {

    private UserDTO user;
    private static LoggedUserDTO loggedUser;

    public static LoggedUserDTO getInstance(UserDTO user){
        if(loggedUser == null && user != null){
            loggedUser = new LoggedUserDTO(user);
        }
        return  loggedUser;
    }

    private LoggedUserDTO(UserDTO user){
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }
}
