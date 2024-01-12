package com.example.easycamp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.example.easycamp.R
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.domain.UserDTO



import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.easycamp.ui.inicioSesion.Login_Activity
import com.example.easycamp.util.DBHelper
import com.google.firebase.auth.FirebaseAuth


class PerfilFragment : Fragment() {

    private lateinit var usuario: UserDTO

    private lateinit var tvNombreUsuario: TextView
    private lateinit var tvTipoUsuario: TextView
    private lateinit var tvNombre: EditText
    private lateinit var tvApellidos: EditText
    private lateinit var tvEdad: EditText
    private lateinit var btnActualizar: Button
    private lateinit var persistencia:DBHelper
    private lateinit var  mAuth: FirebaseAuth;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)
        mAuth=FirebaseAuth.getInstance()
        usuario = obtenerUsuario()
        persistencia=DBHelper(this.context);
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
        return LoggedUserDTO.getInstance(null).user
    }

    private fun actualizarInformacionUsuario() {
        persistencia.actualizarUsuario(usuario,tvNombre.text.toString(),tvApellidos.text.toString(),tvEdad.text.toString())
        Log.d("MiApp", "Información del usuario actualizada: $usuario")
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
        mAuth.signOut()

        startActivity(intent)
        activity?.finish()
    }
}

