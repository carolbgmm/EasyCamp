package com.example.easycamp.ui.detalle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.ui.buscadorCliente.ListaHijosAdapter
import com.example.easycamp.ui.buscadorCliente.RecyclerClienteFragment
import com.example.easycamp.util.DBHelper

class DetalleAsistentesActivity : AppCompatActivity() {

    private lateinit var service: DBHelper
    lateinit var campamento: CampamentoDto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_asistentes)

        service = DBHelper(baseContext)

        campamento =
                intent.getParcelableExtra(RecyclerClienteFragment.CAMPAMENTO_SELECCIONADO)!!

        val listaDeHijos = service.obtenerInscritosDeCampamento(campamento.id).toMutableList()

        val adapter = ListaHijosAdapter()
        adapter.submitList(listaDeHijos)

        Log.d("MiApp", "Se cargo la lista de asistentes ")




    }
}