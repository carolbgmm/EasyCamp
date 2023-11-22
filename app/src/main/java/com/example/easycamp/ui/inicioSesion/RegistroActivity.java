package com.example.easycamp.ui.inicioSesion;

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
import com.example.easycamp.util.DBHelper;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombreUsuario, etContrasena, etNombre, etApellidos, etEdad;
    private Spinner spTipoUsuario;
    private Button btnConfirmar;

    private DBHelper persistencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        persistencia=new DBHelper(this);
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
                            0,
                            etNombreUsuario.getText().toString(),
                            spTipoUsuario.getSelectedItem().toString().toUpperCase(),
                            etNombre.getText().toString(),
                            etApellidos.getText().toString(),
                            Integer.parseInt(etEdad.getText().toString())
                    );

                    String contrasena = etContrasena.getText().toString();
                    Log.d("MiApp", "Se intenta crear usuario  "+etNombreUsuario.getText().toString()+"  "+contrasena);

                    boolean aux=persistencia.crearUsuario(usuario, contrasena);
                    if (aux) {
                        Toast.makeText(RegistroActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegistroActivity.this, Login_Activity.class);
                       startActivity(intent);
                       finish();
                    } else {
                        Toast.makeText(RegistroActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                    }
            }
        }});
    }

    private boolean validarCampos() {
        EditText etNombreUsuario = findViewById(R.id.etNombreUsuario);
        EditText etContrasena = findViewById(R.id.etContrasena);
        EditText etNombre = findViewById(R.id.etNombre);
        EditText etApellidos = findViewById(R.id.etApellidos);
        EditText etEdad = findViewById(R.id.etEdad);


        if (etNombreUsuario.getText().toString().isEmpty()) {
            etNombreUsuario.setError("Nombre de usuario vacío");
            etNombreUsuario.requestFocus();
            return false;
        }


        if (persistencia.existeNombreUsuario(etNombreUsuario.getText().toString())) {
            etNombreUsuario.setError("Nombre de usuario ya en uso");
            etNombreUsuario.requestFocus();
            return false;
        }

        if (etContrasena.getText().toString().isEmpty()) {
            etContrasena.setError("Contraseña vacía");
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
