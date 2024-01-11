package com.example.easycamp.ui.inicioSesion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.easycamp.R;
import com.example.easycamp.domain.UserDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistroActivity extends AppCompatActivity {

    private EditText etNombreUsuario, etContrasena, etNombre, etApellidos, etEdad;
    private Spinner spTipoUsuario;
    private Button btnConfirmar;


    private UserDTO nuevoUsuario;
    private FirebaseAuth mAuth;

    private DatabaseReference mDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        mDataBase= FirebaseDatabase.getInstance().getReference();
        // Inicializar vistas
        etNombreUsuario = findViewById(R.id.etNombreUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etEdad = findViewById(R.id.etEdad);
        spTipoUsuario = findViewById(R.id.spTipoUsuario);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        // Configurar opciones para el spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.tipo_usuario_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoUsuario.setAdapter(adapter);

        // Configurar el listener para el botón Confirmar
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos()) {
                    UserDTO usuario = new UserDTO(
                            "",
                            etNombreUsuario.getText().toString(),
                            spTipoUsuario.getSelectedItem().toString().toUpperCase(),
                            etNombre.getText().toString(),
                            etApellidos.getText().toString(),
                            Integer.parseInt(etEdad.getText().toString()),
                                    etContrasena.getText().toString()
                    );

                    nuevoUsuario=usuario;
                    Log.d("MiApp", "Se intenta crear usuario  "+etNombreUsuario.getText().toString()+"  "+etContrasena.getText().toString());

                    registerUser();

            }
        }});
    }

    private void registerUser() {


        mAuth.createUserWithEmailAndPassword(nuevoUsuario.getNombreUsuario(), nuevoUsuario.getContrasena()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistroActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_LONG).show();
                    String id = mAuth.getCurrentUser().getUid();
                    nuevoUsuario.setId(id);
                    mDataBase.child("usuarios").child(id).setValue(nuevoUsuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){


                                Toast.makeText(RegistroActivity.this, "Usuario guardado exitosamente", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(RegistroActivity.this, Login_Activity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(RegistroActivity.this, "Hubo un problema guardando los datos", Toast.LENGTH_LONG).show();

                            }
                        }
                    });


                } else {
                    Toast.makeText(RegistroActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean validarCampos() {
        EditText etNombreUsuario = findViewById(R.id.etNombreUsuario);
        EditText etContrasena = findViewById(R.id.etContrasena);
        EditText etNombre = findViewById(R.id.etNombre);
        EditText etApellidos = findViewById(R.id.etApellidos);
        EditText etEdad = findViewById(R.id.etEdad);


        if (etNombreUsuario.getText().toString().isEmpty()) {
            etNombreUsuario.setError("Correo vacío");
            etNombreUsuario.requestFocus();
            return false;
        }



        if (etContrasena.getText().toString().isEmpty()) {
            etContrasena.setError("Contraseña vacía");
            etContrasena.requestFocus();
            return false;
        }


        if (etContrasena.length()<6) {
            etContrasena.setError("Contraseña corta , más de 5 caracteres");
            etContrasena.requestFocus();
            return false;
        }

        if (etNombre.getText().toString().isEmpty()) {
            etNombre.setError("Nombre vacío");
            etNombre.requestFocus();
            return false;
        }

        if (etApellidos.getText().toString().isEmpty()) {
            etApellidos.setError("Apellidos vacíos");
            etApellidos.requestFocus();
            return false;
        }

        if (etEdad.getText().toString().isEmpty()) {
            etEdad.setError("Edad vacía");
            etEdad.requestFocus();
            return false;
        }

        int edad = Integer.parseInt(etEdad.getText().toString());
        if (edad < 18 || edad > 100) {
            etEdad.setError("La edad debe estar entre 18 y 100 años");
            etEdad.requestFocus();
            return false;
        }

        return true;
    }





}
