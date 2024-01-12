package com.example.easycamp.ui.buscadorTrabajador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.easycamp.R
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.domain.UserDTO


class HerramientasFragment : androidx.fragment.app.Fragment() {

    private lateinit var usuarioActual: UserDTO


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_herramientas, container, false)

        usuarioActual = obtenerUsuarioActual()

        val btnListaAsist: Button = view.findViewById(R.id.button_lista_asistentes)
        val btnAceptados: Button = view.findViewById(R.id.button_aceptados)
        val btnUbis: Button = view.findViewById(R.id.button_ubicaciones)
        val btnTareas: Button = view.findViewById(R.id.button_tareas)

        btnListaAsist.setOnClickListener {
            mostrarListaAsistentes()
        }


        btnAceptados.setOnClickListener {
            mostrarAceptados()
        }

        btnUbis.setOnClickListener {
            mostrarUbis()
        }

        btnTareas.setOnClickListener {
           mostrarTareas()
        }
        
        return view
    }

    private fun mostrarAceptados() {
        val listAceptadosFragment = ListaAceptadosFragment.newInstance()
        fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container_trabajador, listAceptadosFragment)
                ?.addToBackStack(null)
                ?.commit()
    }

    private fun mostrarUbis() {
        val ubicacionesTrabajadorFragment = UbicacionesFragment.newInstance()
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container_trabajador, ubicacionesTrabajadorFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun mostrarTareas() {
        val tareasTrabajadorFragment = TareasTrabajadorFragment.newInstance()
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container_trabajador, tareasTrabajadorFragment)
            ?.addToBackStack(null)
            ?.commit()
    }


    private fun obtenerUsuarioActual(): UserDTO {

        return LoggedUserDTO.getInstance(null).user;
    }

    private fun mostrarListaAsistentes() {
        val listAsistFragment = ListaAsistentesFragment.newInstance()
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container_trabajador, listAsistFragment)
            ?.addToBackStack(null)
            ?.commit()
    }


    companion object {
        fun newInstance(): HerramientasFragment{
            return HerramientasFragment()
        }
    }
}