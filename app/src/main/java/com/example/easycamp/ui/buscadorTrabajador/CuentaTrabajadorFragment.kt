package com.example.easycamp.ui.buscadorTrabajador

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.easycamp.R
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.ui.PerfilFragment
import com.example.easycamp.ui.inicioSesion.Login_Activity
import com.example.easycamp.util.crud.FirebaseUserManager
import com.google.firebase.auth.FirebaseAuth


class CuentaTrabajadorFragment : Fragment() {
    private lateinit var usuarioActual: UserDTO
    private lateinit var  mAuth: FirebaseAuth;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cuenta_trabajador, container, false)
        mAuth=FirebaseAuth.getInstance()
        usuarioActual = obtenerUsuarioActual()

        val btnPerfil: Button = view.findViewById(R.id.btnPerfil)
        val btnCerrarSesion: Button = view.findViewById(R.id.btnCerrarSesion)

        btnPerfil.setOnClickListener {
            // Acción para el botón Perfil
            mostrarInformacionPerfil()
        }


        btnCerrarSesion.setOnClickListener {
            // Acción para el botón Cerrar Sesión
            cerrarSesion()
        }

        return view
    }

    private fun obtenerUsuarioActual(): UserDTO {
        val firebaseUserManager = FirebaseUserManager()
        var usuarioActual: UserDTO? = null

        firebaseUserManager.obtenerUsuarioActual(object : FirebaseUserManager.OnUserDTOReceivedListener {
            override fun onUserDTOReceived(userDTO: UserDTO?) {
                userDTO?.let {
                    usuarioActual = it
                }
            }
        })

        return usuarioActual ?: throw IllegalStateException("No se pudo obtener el usuario actual")
    }


    private fun mostrarInformacionPerfil() {
        val perfilFragment = PerfilFragment.newInstance()
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container_trabajador, perfilFragment)
            ?.addToBackStack(null)
            ?.commit()
    }



    private fun cerrarSesion() {
        Toast.makeText(requireContext(), "Cerrando sesión...", Toast.LENGTH_SHORT).show()

        val intent = Intent(activity, Login_Activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        mAuth.signOut()
        startActivity(intent)
        activity?.finish()
    }

    companion object {
        fun newInstance(): CuentaTrabajadorFragment{
            return CuentaTrabajadorFragment()
        }
    }
}