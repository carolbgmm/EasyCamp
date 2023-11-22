package com.example.easycamp.ui.inicioSesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.easycamp.R;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombreUsuario, etContrasena, etNombre, etApellidos, etEdad;
    private Spinner spTipoUsuario;
    private Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

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
                if (validarCampos()) {
                    // Lógica para enviar datos a la actividad de inicio de sesión
                    enviarDatosInicioSesion();
                } else {
                    // Mostrar un mensaje de error o realizar alguna acción adicional
                    Toast.makeText(RegistroActivity.this, "Completa todos los campos correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean validarCampos() {

        return true;
    }


    private void enviarDatosInicioSesion() {
      //base de datos

        Intent intent = new Intent(RegistroActivity.this, Login_Activity.class);
        startActivity(intent);
        finish();
    }
}
