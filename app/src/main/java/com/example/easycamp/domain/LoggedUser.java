package com.example.easycamp.domain;

public class LoggedUser {

    private UserDTO user;
    private static LoggedUser loggedUser;

    public static LoggedUser getInstance(UserDTO user){
        if(loggedUser == null){
            loggedUser = new LoggedUser(user);
        }
        return  loggedUser;
    }

    private LoggedUser(UserDTO user){
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }
}
