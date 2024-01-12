package com.example.easycamp.ui.buscadorTrabajador

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.ui.detalle.DetalleAsistentesActivity
import com.example.easycamp.util.DBHelper


class ListaAsistentesFragment : Fragment() {

    private lateinit var usuarioActual: UserDTO

    private lateinit var recyclerCamp: RecyclerView
    private lateinit var persistencia: DBHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lista_asistentes, container, false)

        usuarioActual = obtenerUsuarioActual()
        persistencia = DBHelper.getInstance(context)
        recyclerCamp = view.findViewById(R.id.recycler_campamentos_trabajador)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lista = persistencia.obtenerInscritosDeTrabajador(LoggedUserDTO.getInstance(null).user.id)
        val adapterCampamentos = BuscadorTrabajadorAdapter(lista, object : BuscadorTrabajadorAdapter.OnItemClickListener {
            override fun onItemClick(campamento: CampamentoDto?) {
                campamento?.let { clickonItem(campamento) }
            }
        })
        recyclerCamp.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterCampamentos

        }

    }

    private fun obtenerUsuarioActual(): UserDTO {

        return LoggedUserDTO.getInstance(null).user;
    }

    companion object {
        fun newInstance(): ListaAsistentesFragment{
            return ListaAsistentesFragment()
        }
    }

    fun clickonItem(campamento: CampamentoDto) {
        val intent = Intent(activity, DetalleAsistentesActivity::class.java)
        intent.putExtra(RecyclerTrabajadorFragment.CAMPAMENTO_SELECCIONADO, campamento)
        startActivity(intent)
    }


}