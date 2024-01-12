package com.example.easycamp.ui.coordinador

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.util.DBHelper

class ListaCampamentosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnCrearCampamento: Button
    private lateinit var service: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        service = DBHelper(context)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lista_campamentos, container, false)

        // Obtener referencias de los elementos en el layout
        recyclerView = view.findViewById(R.id.recyclerViewCampamentos)
        btnCrearCampamento = view.findViewById(R.id.btnCrearCampamento)

        // Configurar el RecyclerView con un LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Configurar la lista de campamentos
        val campamentos: List<CampamentoDto> = service.obtenerCampamentosCoordinador()

        // Adaptador para el RecyclerView (puedes personalizar esto según tus necesidades)
        val adapter = CampamentosAdapter(campamentos, object : CampamentosAdapter.OnItemClickListener {
            override fun onItemClick(campamento: CampamentoDto) {
                // Acción a realizar cuando se hace clic en un campamento específico
                // Puedes abrir una pantalla de detalles o realizar alguna otra acción
                // dependiendo del campamento seleccionado
            }
        })

        // Configurar el RecyclerView con el adaptador
        recyclerView.adapter = adapter

        // Configurar el evento clic del botón para añadir un nuevo campamento
        btnCrearCampamento.setOnClickListener {
            // Aquí puedes agregar la lógica para llevar al coordinador al formulario de creación de campamento
        }

        return view
    }

    companion object {
        fun newInstance(): ListaCampamentosFragment {
            return ListaCampamentosFragment()
        }
    }
}
