package com.example.easycamp.ui.detalle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.ui.buscadorCliente.ListaHijosAdapter
import com.example.easycamp.ui.buscadorCliente.RecyclerClienteFragment
import com.example.easycamp.util.DBHelper

class DetalleAsistentesActivity : AppCompatActivity() {

    private lateinit var service: DBHelper
    lateinit var campamento: CampamentoDto
    private lateinit var recyclerCamp: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_asistentes)

        service = DBHelper(baseContext)
        recyclerCamp = findViewById(R.id.recyclerViewAsistentes)

        campamento =
                intent.getParcelableExtra(RecyclerClienteFragment.CAMPAMENTO_SELECCIONADO)!!

        val listaDeHijos = service.obtenerInscritosDeCampamento(campamento.id).toMutableList()

        val adapterHijos = ListaHijosAdapter()
        adapterHijos.submitList(listaDeHijos)

        recyclerCamp.apply {
            setHasFixedSize(true)
            adapter = adapterHijos

        }

        recyclerCamp.layoutManager = LinearLayoutManager(this)

        Log.d("MiApp", "Se cargo la lista de asistentes ")




    }
}