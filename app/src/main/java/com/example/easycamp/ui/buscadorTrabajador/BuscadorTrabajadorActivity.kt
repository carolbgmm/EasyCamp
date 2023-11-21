package com.example.easycamp.ui.buscadorTrabajador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.ui.detalle.DetalleCampamentoActivity
import com.example.easycamp.util.DBHelper


class BuscadorTrabajadorActivity : AppCompatActivity() {
    var recyclerCamp: RecyclerView? = null
    lateinit var persistencia: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscador_trabajador)
        persistencia= DBHelper(this)
        recyclerCamp = findViewById(R.id.recyclerCampamentos)
        recyclerCamp?.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerCamp?.setLayoutManager(layoutManager)

        val lista = getAllCampamentos().let { it }.orEmpty()
        var lpAdapter =
            BuscadorTrabajadorAdapter(lista, object : BuscadorTrabajadorAdapter.OnItemClickListener {
                override fun onItemClick(campamento: CampamentoDto?) {
                    campamento?.let { clickonItem(campamento) }
                }
            })

        recyclerCamp?.adapter = lpAdapter
    }

    fun getAllCampamentos(): List<CampamentoDto> {
        var listaCampamentos = persistencia.obtenerCampamentosConFavoritos(LoggedUserDTO.getInstance(null).user.id)

        return listaCampamentos
    }

    fun clickonItem(campamento: CampamentoDto) {
        val intent = Intent(this@BuscadorTrabajadorActivity, DetalleCampamentoActivity::class.java)
        intent.putExtra(CAMPAMENTO_SELECCIONADO, campamento)
        startActivity(intent)
    }

    companion object {
        val CAMPAMENTO_SELECCIONADO = "campamento_seleccionado"
    }
}