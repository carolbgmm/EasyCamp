package com.example.easycamp.ui.buscadorCliente

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.HijoDTO
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.util.DBHelper

class ListaHijosFragment : Fragment() {
    private lateinit var service: DBHelper
    private lateinit var usuarioActual: UserDTO
    private lateinit var listaDeHijos: MutableList<HijoDTO>

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregarHijo: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_hijos, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewHijos)
        btnAgregarHijo = view.findViewById(R.id.btnAgregarHijo)

        service = DBHelper(requireContext())
        usuarioActual = obtenerUsuarioActual()
        listaDeHijos = service.obtenerHijosPorUsuario(usuarioActual.id).toMutableList()

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        val adapter = ListaHijosAdapter()
        recyclerView.adapter = adapter
        adapter.submitList(listaDeHijos)

        adapter.setOnDeleteClickListener { position ->
            // Lógica para eliminar el hijo en la posición dada
            val hijoAEliminar = listaDeHijos[position]
            eliminarHijo(hijoAEliminar, adapter)
        }

        btnAgregarHijo.setOnClickListener {
            val agregarHijoFragment = AgregarHijoFragment(adapter, listaDeHijos, usuarioActual)
            agregarHijoFragment.show(requireFragmentManager(), "AgregarHijoFragment")
        }

        return view
    }

    private fun eliminarHijo(hijo: HijoDTO, adapter: ListaHijosAdapter) {
        // Lógica para eliminar el hijo, por ejemplo, desde tu base de datos o lista
        // Después de eliminar el hijo, actualizas tu lista de hijos y notificas al adaptador
        //service.eliminarHijo(hijo) // Ajusta esto según tu lógica
        listaDeHijos.remove(hijo)
        adapter.submitList(listaDeHijos)
        adapter.notifyDataSetChanged()
    }


    private fun obtenerUsuarioActual(): UserDTO {
        return LoggedUserDTO.getInstance(null).user;
    }

    companion object {
        fun newInstance(): ListaHijosFragment {
            return ListaHijosFragment()
        }
    }
}
