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
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.ui.PerfilFragment
import com.example.easycamp.ui.inicioSesion.Login_Activity


class CuentaClienteFragment : Fragment() {

    private lateinit var usuarioActual: UserDTO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cuenta_cliente, container, false)

        usuarioActual = obtenerUsuarioActual()

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

    private fun obtenerUsuarioActual(): UserDTO {

        return LoggedUserDTO.getInstance(null).user;
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
        startActivity(intent)
        activity?.finish()
    }

    companion object {
        fun newInstance(): CuentaClienteFragment {
            return CuentaClienteFragment()
        }
    }
}
