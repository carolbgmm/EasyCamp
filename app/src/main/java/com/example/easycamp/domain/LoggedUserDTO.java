package com.example.easycamp.domain;

import android.util.Log;

public class LoggedUserDTO {

    private UserDTO user;
    private static LoggedUserDTO loggedUser;

    public static LoggedUserDTO getInstance(UserDTO user){
        if(loggedUser == null && user != null){
            loggedUser = new LoggedUserDTO(user);
            Log.d("MiApp", "LoggedUser: "+user.getId()+"  "+user.getNombreUsuario());
        }
        return  loggedUser;
    }


    private LoggedUserDTO(UserDTO user){
        this.user = user;
    }

    public static void restart(){
        loggedUser = null;
    }

    public UserDTO getUser() {
        return user;
    }
}
