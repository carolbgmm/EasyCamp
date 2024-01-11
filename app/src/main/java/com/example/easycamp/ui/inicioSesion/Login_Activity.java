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

import com.example.easycamp.R;
import com.example.easycamp.domain.UserDTO;
import com.example.easycamp.ui.buscadorCliente.BuscadorClienteActivity;
import com.example.easycamp.ui.buscadorTrabajador.BuscadorTrabajadorActivity;
import com.example.easycamp.util.crud.FirebaseUserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private FirebaseAuth mAuth;
    private FirebaseUserManager firebaseUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        firebaseUserManager = new FirebaseUserManager();

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

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

                signInWithEmailAndPassword(username, password);
            }
        });
    }

    private void signInWithEmailAndPassword(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            getUserInfo();
                        } else {
                            showToast("Authentication failed. Check your credentials.");
                        }
                    }
                });
    }

    private void getUserInfo() {
        firebaseUserManager.obtenerUsuarioActual(new FirebaseUserManager.OnUserDTOReceivedListener() {
            @Override
            public void onUserDTOReceived(UserDTO userDTO) {
                if (userDTO != null) {
                    redirigir(userDTO);
                } else {
                    showToast("Error retrieving user information.");
                }
            }
        });
    }

    private void redirigir(UserDTO usuario) {
        String tipoUsuario = usuario.getTipoUsuario();
        Log.d("MiApp", "Tipo usuario " + tipoUsuario);

        switch (tipoUsuario) {
            case "CLIENTE":
                startActivity(new Intent(Login_Activity.this, BuscadorClienteActivity.class));
                break;
            case "TRABAJADOR":
                startActivity(new Intent(Login_Activity.this, BuscadorTrabajadorActivity.class));
                break;
            default:
                showToast("Error: Unknown user type.");
                break;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
