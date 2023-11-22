package com.example.easycamp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.easycamp.R
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.domain.UserDTO

class PerfilFragment : Fragment() {

    private lateinit var usuario: UserDTO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        // Aquí obtén el usuario de algún lugar, ya sea a través de argumentos, ViewModel, o alguna otra forma
        usuario = obtenerUsuario()

        // Llena tus TextView con la información del usuario
        Log.d("MiApp", "Se va a msotrar la informacion del Usuario ")

        view?.findViewById<TextView>(R.id.tvNombreUsuario)?.text = "Nombre de Usuario: ${usuario.nombreUsuario}"
            view?.findViewById<TextView>(R.id.tvTipoUsuario)?.text = "Tipo de Usuario: ${usuario.tipoUsuario}"
            view?.findViewById<TextView>(R.id.tvNombre)?.text = "Nombre: ${usuario.nombre}"
            view?.findViewById<TextView>(R.id.tvApellidos)?.text = "Apellidos: ${usuario.apellidos}"
            view?.findViewById<TextView>(R.id.tvEdad)?.text = "Edad: ${usuario.edad}"



        return view
    }

    private fun obtenerUsuario(): UserDTO {

        return LoggedUserDTO.getInstance(null).user;
    }

    companion object {
        fun newInstance(): Fragment {
            return PerfilFragment()
        }
    }
}
