package com.example.easycamp.ui.coordinador

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.ui.buscadorCliente.ListaHijosAdapter
import com.example.easycamp.ui.buscadorCliente.RecyclerClienteFragment
import com.example.easycamp.util.DBHelper

private const val ARG_PARAM1 = "idCampamento"

class ListaAsistentesCoorFragment : Fragment() {

    private lateinit var service: DBHelper
    private lateinit var recyclerCamp: RecyclerView
    private var campamentoDto: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            campamentoDto = it.getLong(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_lista_asistentes_coor, container, false)

        service = DBHelper(context)
        recyclerCamp = view.findViewById(R.id.recyclerViewAsistentes)

        val listaDeHijos = service.obtenerInscritosDeCampamento(campamentoDto).toMutableList()

        val adapterHijos = ListaHijosAdapter()
        adapterHijos.submitList(listaDeHijos)
        recyclerCamp.layoutManager = LinearLayoutManager(activity)

        recyclerCamp.apply {
            setHasFixedSize(true)
            adapter = adapterHijos

        }

        Log.d("MiApp", "Se cargo la lista de asistentes en coordinador")

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(idCampamento: Long) =
                ListaAsistentesCoorFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ARG_PARAM1, idCampamento)
                    }
                }

        fun nenwIstance(id: Long): Fragment {
            return ListaAsistentesCoorFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, id)
                }
            }
        }
    }


}