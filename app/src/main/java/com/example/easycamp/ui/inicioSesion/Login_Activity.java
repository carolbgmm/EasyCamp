package com.example.easycamp.ui.inicioSesion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easycamp.domain.LoggedUser;
import com.example.easycamp.domain.LoginService;
import com.example.easycamp.R;
import com.example.easycamp.domain.UserDTO;
import com.example.easycamp.ui.buscadorCliente.BuscadorClienteActivity;
import com.example.easycamp.ui.buscadorTrabajador.BuscadorTrabajadorActivity;

public class Login_Activity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private LoginService service;
    private UserDTO user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.service=new LoginService(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (checkCredentials(username, password)) {

                    redirigir(service.getUser());
                } else {
                    showToast(getString(R.string.user_not_found));
                }
            }
        });

        Log.d("MiApp", "Se creo la activity del login ");
    }

    private boolean checkCredentials(String username, String password) {
        return service.checkCredentials(username,password);
    }

    private void redirigir(UserDTO usuario) {

        LoggedUser.getInstance(usuario);
        String tipoUsuario=usuario.getTipoUsuario();
        //hay que enviar user(es un dto) al resto de las pantallas , de esta menera es mas comodo usar sus datos
        switch (tipoUsuario) {
            case "CLIENTE":
                startActivity(new Intent(this, BuscadorClienteActivity.class));
                break;
            case "TRABAJADOR":
                // Redirige a la página principal TRABAJADOR
                startActivity(new Intent(this, BuscadorTrabajadorActivity.class));
                break;
            default:
                showToast("A ocurrido un error, no se encuentra su tipo de ususario");
                break;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
