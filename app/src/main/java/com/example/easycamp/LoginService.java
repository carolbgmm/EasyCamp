package com.example.easycamp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class LoginService {
    private final Login_Activity loginActivity;
    //por ahora el documento simula la base de datos

    private List<UserDTO> users;
    public LoginService(Login_Activity loginActivity) {
       this.loginActivity=loginActivity;
        this.users= getAllUserData();

    }
    public boolean checkCredentials(String username, String password) {
        for (UserDTO aux:this.users
             ) {
            if(username.equals(aux.username) & password.equals(aux.password)){
                return true;
            }

        }
        return false;
    }



    public UserDTO getUser(String username) {
        for (UserDTO aux:this.users
        ) {
            if(username.equals(aux.username)){
                return aux;
            }

        }
        return null;
    }


    public List<UserDTO> getAllUserData(){
        List<UserDTO> userDTOList = new ArrayList<>();



            try {


                InputStream raw = loginActivity.getResources().openRawResource(R.raw.usuarios);
                BufferedReader  b = new BufferedReader(new InputStreamReader(raw, "UTF8"));
                String line;
                while((line = b.readLine())!=null) {
                    String[] valores = line.split(";");
                    UserDTO userDTO = new UserDTO();
                    userDTO.username = valores[0];
                    userDTO.password = valores[1];
                    userDTO.userType = valores[2];
                    userDTOList.add(userDTO);
                }
                b.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        return userDTOList;
    }
    }

