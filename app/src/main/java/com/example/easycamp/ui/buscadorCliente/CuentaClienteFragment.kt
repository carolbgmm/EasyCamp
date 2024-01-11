package com.example.easycamp.ui.buscadorCliente

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.easycamp.R

import android.widget.Button
import android.widget.Toast
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.ui.PerfilFragment
import com.example.easycamp.ui.inicioSesion.Login_Activity
import com.example.easycamp.util.crud.FirebaseUserManager
import com.google.firebase.auth.FirebaseAuth


// CuentaClienteFragment.kt
class CuentaClienteFragment : Fragment(), CuentaClienteFragmentListener {

    private lateinit var usuarioActual: UserDTO
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cuenta_cliente, container, false)

        mAuth = FirebaseAuth.getInstance()
        obtenerUsuarioActual(this)

        val btnPerfil: Button = view.findViewById(R.id.btnPerfil)
        val btnHijos: Button = view.findViewById(R.id.btnHijos)
        val btnCerrarSesion: Button = view.findViewById(R.id.btnCerrarSesion)

        btnPerfil.setOnClickListener {
            // Acción para el botón Perfil
            mostrarInformacionPerfil()
        }

        btnHijos.setOnClickListener {
            // Acción para el botón Hijos
            mostrarListaDeHijos()
        }

        btnCerrarSesion.setOnClickListener {
            // Acción para el botón Cerrar Sesión
            cerrarSesion()
        }

        return view
    }

    private fun obtenerUsuarioActual(listener: CuentaClienteFragmentListener) {
        val firebaseUserManager = FirebaseUserManager()

        firebaseUserManager.obtenerUsuarioActual(object : FirebaseUserManager.OnUserDTOReceivedListener {
            override fun onUserDTOReceived(userDTO: UserDTO?) {
                listener.onUserDTOReceived(userDTO)
            }
        })
    }

    override fun onUserDTOReceived(userDTO: UserDTO?) {
        userDTO?.let {
            usuarioActual = it
            // Ahora puedes utilizar el usuarioActual aquí
        } ?: run {
            Toast.makeText(requireContext(), "No se pudo obtener el usuario actual", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarInformacionPerfil() {
        val perfilFragment = PerfilFragment.newInstance()
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container_cliente, perfilFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun mostrarListaDeHijos() {
        val listaHijosFragment = ListaHijosFragment.newInstance()

        // Usa el FragmentManager para mostrar el fragmento
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container_cliente, listaHijosFragment)
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
        fun newInstance(): CuentaClienteFragment {
            return CuentaClienteFragment()
        }
    }


}
interface CuentaClienteFragmentListener {
    fun onUserDTOReceived(userDTO: UserDTO?)
}