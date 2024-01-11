package com.example.easycamp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.example.easycamp.R
import com.example.easycamp.domain.UserDTO



import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.easycamp.ui.inicioSesion.Login_Activity
import com.example.easycamp.util.crud.FirebaseUserManager
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.CountDownLatch


class PerfilFragment : Fragment() {

    private lateinit var usuario: UserDTO

    private lateinit var tvNombreUsuario: TextView
    private lateinit var tvTipoUsuario: TextView
    private lateinit var tvNombre: EditText
    private lateinit var tvApellidos: EditText
    private lateinit var tvEdad: EditText
    private lateinit var btnActualizar: Button



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        usuario = obtenerUsuario()
        tvNombreUsuario = view.findViewById(R.id.tvValorNombreUsuario)
        tvTipoUsuario = view.findViewById(R.id.tvValorTipoUsuario)
        tvNombre = view.findViewById(R.id.etNombre)
        tvApellidos = view.findViewById(R.id.etApellidos)
        tvEdad = view.findViewById(R.id.etEdad)

        // Llena los TextView con la información del usuario
        tvNombreUsuario.text = usuario.nombreUsuario
        tvTipoUsuario.text = usuario.tipoUsuario
        tvNombre.setText(usuario.nombre)
        tvApellidos.setText(usuario.apellidos)
        tvEdad.setText(usuario.edad.toString())

        btnActualizar = view.findViewById(R.id.btnActualizar)
        btnActualizar.setOnClickListener {
            // Aquí debes implementar la lógica para actualizar la información del usuario
            actualizarInformacionUsuario()
            Toast.makeText(requireContext(), "Usuario Actualizado", Toast.LENGTH_SHORT).show()
            //cerrarSesion()
        }

        return view
    }

    private fun obtenerUsuario(): UserDTO {
        // Instancia de FirebaseUserManager (asegúrate de haberla inicializado)
        val firebaseUserManager = FirebaseUserManager()

        // Obtener el usuario actual utilizando FirebaseUserManager
        var userDTO: UserDTO? = null
        val latch = CountDownLatch(1) // Para sincronizar el hilo principal

        firebaseUserManager.obtenerUsuarioActual(object : FirebaseUserManager.OnUserDTOReceivedListener {
            override fun onUserDTOReceived(usuario: UserDTO?) {
                userDTO = usuario
                latch.countDown()
            }
        })

        try {
            // Esperar hasta que se obtenga la respuesta del hilo secundario
            latch.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // Devolver el UserDTO obtenido
        return userDTO ?: UserDTO() // Devuelve un UserDTO vacío si no se obtuvo correctamente
    }


    private fun actualizarInformacionUsuario() {
        // Instancia de FirebaseUserManager (asegúrate de haberla inicializado)
        val firebaseUserManager = FirebaseUserManager()

        // Obtener el usuario actual utilizando FirebaseUserManager
        firebaseUserManager.obtenerUsuarioActual(object : FirebaseUserManager.OnUserDTOReceivedListener {
            override fun onUserDTOReceived(usuario: UserDTO?) {
                // Verificar si se obtuvo correctamente el usuario actual
                if (usuario != null) {
                    // Actualizar la información del usuario con los nuevos valores
                    usuario.nombre = tvNombre.text.toString()
                    usuario.apellidos = tvApellidos.text.toString()
                    usuario.edad = tvEdad.text.toString().toInt() // Asegúrate de manejar excepciones si la conversión falla

                    // Llamar al método de FirebaseUserManager para actualizar el usuario
                    firebaseUserManager.actualizarUsuario(usuario)

                    Log.d("MiApp", "Información del usuario actualizada: $usuario")
                } else {
                    // No se pudo obtener el usuario actual
                    Log.d("MiApp", "No se pudo obtener el usuario actual para actualizar la información")
                }
            }
        })
    }


    companion object {
        fun newInstance(): Fragment {
            return PerfilFragment()
        }
    }

    private fun cerrarSesion() {
        Toast.makeText(requireContext(), "Cerrando sesión...", Toast.LENGTH_SHORT).show()

        val intent = Intent(activity, Login_Activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val firebaseUserManager = FirebaseUserManager()
        firebaseUserManager.cerrarSesion();

        startActivity(intent)
        activity?.finish()
    }
}

