package com.example.easycamp.ui.buscadorTrabajador

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDTO
import com.example.easycamp.ui.buscadorCliente.RecyclerClienteFragment
import com.example.easycamp.ui.detalle.DetalleCampamentoActivity

class InscritosTrabajadorFragment : Fragment() {

    private lateinit var recyclerCamp: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_inscritos_trabajador, container, false)


        recyclerCamp= view.findViewById(R.id.recycler_campamentos_inscrito)
        // Inflate the layout for this fragment
        return view
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            InscritosTrabajadorFragment().apply {
            }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val lista =
//            persistencia.obtenerInscritosDeTrabajador(LoggedUserDTO.getInstance(null).user.id)
//        val adapterCampamentos =
//            BuscadorTrabajadorAdapter(lista, object : BuscadorTrabajadorAdapter.OnItemClickListener {
//                override fun onItemClick(campamento: CampamentoDto?) {
//                    campamento?.let { clickonItem(campamento) }
//                }
//            })
//        recyclerCamp.apply {
//            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(activity)
//            adapter = adapterCampamentos
//
//        }
//    }
    fun clickonItem(campamento: CampamentoDTO) {
        val intent = Intent(activity, DetalleCampamentoActivity::class.java)
        intent.putExtra(RecyclerClienteFragment.CAMPAMENTO_SELECCIONADO, campamento)
        startActivity(intent)
    }
}