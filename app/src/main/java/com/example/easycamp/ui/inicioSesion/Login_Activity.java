package com.example.easycamp.ui.inicioSesion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easycamp.domain.LoggedUserDTO;
import com.example.easycamp.domain.LoginService;
import com.example.easycamp.R;
import com.example.easycamp.domain.UserDTO;
import com.example.easycamp.ui.buscadorCliente.BuscadorClienteActivity;
import com.example.easycamp.ui.buscadorTrabajador.BuscadorTrabajadorActivity;
import com.example.easycamp.ui.coordinador.CoordinadorActivity;
import com.example.easycamp.util.DBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private LoginService service;
    private DBHelper persistencia;

    private FirebaseAuth mAuth;
    private UserDTO user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        this.service=new LoginService(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        persistencia=new DBHelper(this);
        persistencia.sincronizarUsuarios();
        mAuth=FirebaseAuth.getInstance();
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Activity.this, RegistroActivity.class));
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (checkCredentials(username, password)) {
                    mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            redirigir(service.getUser());
                        }
                    });

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
        LoggedUserDTO.getInstance(usuario);
        String tipoUsuario=usuario.getTipoUsuario();
        Log.d("MiApp", "tipo usuario "+tipoUsuario);
        //hay que enviar user(es un dto) al resto de las pantallas , de esta menera es mas comodo usar sus datos
        switch (tipoUsuario) {
            case "CLIENTE":
                startActivity(new Intent(this, BuscadorClienteActivity.class));
                break;
            case "TRABAJADOR":
                // Redirige a la página principal TRABAJADOR
                startActivity(new Intent(this, BuscadorTrabajadorActivity.class));
                break;
            case "COORDINADOR":
                // Redirige a la página principal COORDINADOR
                startActivity(new Intent(this, CoordinadorActivity.class));
                break;
            default:
                showToast("Ha ocurrido un error, no se encuentra su tipo de ususario");
                break;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
