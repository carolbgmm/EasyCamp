package com.example.easycamp.ui.buscadorCliente

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.domain.HijoDTO
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.ui.detalle.DetalleCampamentoActivity
import com.example.easycamp.util.DBHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ApuntarHijosActivity : AppCompatActivity() {

    private lateinit var service: DBHelper
    private lateinit var listaDeHijos: MutableList<HijoDTO>
    private var campamento: CampamentoDto? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apuntar_hijos)

        recyclerView = findViewById(R.id.recyclerViewHijos)

        campamento =
            intent.getParcelableExtra(RecyclerClienteFragment.CAMPAMENTO_SELECCIONADO)

        service = DBHelper(baseContext)
        listaDeHijos = service.obtenerHijosPorUsuario(LoggedUserDTO.getInstance(null).user.id).toMutableList()
        Log.d("MiApp", "Se cargo la lista de hijos ")

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        campamento?.let { c->
            listaDeHijos.removeIf{
                it.edad < c.edad_minima || it.edad > c.edad_maxima
            }
            val adapter = ListaApuntarHijosAdapter(object: ListaApuntarHijosAdapter.MyOnClickListener {
                override fun onClick(item: HijoDTO?) {
                     clickonItem(item)
                }
            })
            recyclerView.adapter = adapter
            adapter.submitList(listaDeHijos)
        }

    }

    fun clickonItem(hijo: HijoDTO?) {
        hijo?.let { h->
            campamento?.let { c->
                service.inscribirHijo(h.id, c.id)
            }
        }

    }
}