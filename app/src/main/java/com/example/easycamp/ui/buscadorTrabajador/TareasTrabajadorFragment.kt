package com.example.easycamp.ui.buscadorTrabajador

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.domain.TareaDTO
import com.example.easycamp.util.DBHelper

class TareasTrabajadorFragment : Fragment() {
    private lateinit var recyclerCamp: RecyclerView
    private lateinit var persistencia: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_tareas_trabajador, container, false)

        persistencia = DBHelper.getInstance(context)
        recyclerCamp= view.findViewById(R.id.recycler_tareas_trabajador)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lista = persistencia.obtenerTareasDeUsuario(LoggedUserDTO.getInstance(null).user.nombreUsuario)
        val adapterTareas = TareasTrabajadorAdapter(lista, object : TareasTrabajadorAdapter.OnItemClickListener {
            override fun onItemClick(tarea: TareaDTO?) {
                tarea?.let { clickonItem(tarea) }
            }
        })
        recyclerCamp.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterTareas

        }

    }

    companion object {
        //val TAREA_SELECCIONADO = "tarea_seleccionada"
        @JvmStatic
        fun newInstance() =
            TareasTrabajadorFragment()
    }

    fun clickonItem(tarea: TareaDTO) {
//        val intent = Intent(activity, DetalleCampamentoActivity::class.java)
//        intent.putExtra(TAREA_SELECCIONADO, TAREA_SELECCIONADO)
//        startActivity(intent)
    }

}