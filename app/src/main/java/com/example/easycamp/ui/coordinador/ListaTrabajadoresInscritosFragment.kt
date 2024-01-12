package com.example.easycamp.ui.coordinador

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.util.DBHelper

private const val ARG_PARAM1 = "campamento"
class ListaTrabajadoresInscritosFragment : Fragment() {

    private var campamentoDto: CampamentoDto? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            campamentoDto = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_trabajadores_inscritos, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTrabajadores)

        val adapter = TrabajadoresInscritosAdapter(campamentoDto!!)
        val persistencia = DBHelper.getInstance(context)
        val listaTrabajadores =  persistencia.obtenerTrabajadoresInscritosDeCampamento(campamentoDto!!.id)
        adapter.submitList(listaTrabajadores)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(campamentoDto: CampamentoDto) =
            ListaTrabajadoresInscritosFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, campamentoDto)
                }
            }
    }
}