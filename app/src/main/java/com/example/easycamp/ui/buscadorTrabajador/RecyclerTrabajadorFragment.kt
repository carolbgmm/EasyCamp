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
import com.example.easycamp.ui.detalle.DetalleCampamentoActivity
import com.example.easycamp.util.DBHelper
class RecyclerTrabajadorFragment : Fragment() {
    private lateinit var recyclerCamp: RecyclerView
    private lateinit var persistencia: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_trabajador, container, false)

        persistencia = DBHelper.getInstance(context)
        recyclerCamp= view.findViewById(R.id.recycler_campamentos_trabajador)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lista = persistencia.obtenerCampamentosConFavoritos(LoggedUserDTO.getInstance(null).user.id)
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

    companion object {
        val CAMPAMENTO_SELECCIONADO = "campamento_seleccionado"
        @JvmStatic
        fun newInstance() =
            RecyclerTrabajadorFragment()
    }

    fun clickonItem(campamento: CampamentoDto) {
        val intent = Intent(activity, DetalleCampamentoActivity::class.java)
        intent.putExtra(CAMPAMENTO_SELECCIONADO, campamento)
        startActivity(intent)
    }
}