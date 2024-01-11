package com.example.easycamp.domain;

import com.example.easycamp.R;
import com.example.easycamp.ui.inicioSesion.Login_Activity;
import com.example.easycamp.util.DBHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class LoginService {
    private final Login_Activity loginActivity;
    //por ahora el documento simula la base de datos

    private UserDTO user;

    private DBHelper persistencia;
    public LoginService(Login_Activity loginActivity) {
       this.loginActivity=loginActivity;
        this.persistencia=new DBHelper(loginActivity);

    }
    public boolean checkCredentials(String username, String password) {
        user=persistencia.obtenerUsuarioPorCredenciales(username,password);
        if(user!=null){
            return true;
        }
        return false;
    }


    public UserDTO getUser() {
       return user;
    }


    }

