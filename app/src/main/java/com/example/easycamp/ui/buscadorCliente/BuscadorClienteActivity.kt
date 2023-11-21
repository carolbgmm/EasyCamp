package com.example.easycamp.ui.buscadorCliente

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.domain.LoggedUser
import com.example.easycamp.ui.DetalleCampamentoActivity
import com.example.easycamp.util.DBHelper
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class BuscadorClienteActivity : AppCompatActivity() {

    var recyclerCamp: RecyclerView? = null
    lateinit var persistencia: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscador_cliente)
        persistencia= DBHelper(this)
        recyclerCamp = findViewById(R.id.recyclerCampamentos)
        recyclerCamp?.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerCamp?.setLayoutManager(layoutManager)

        val lista = getAllCampamentos().let { it }.orEmpty()
        var lpAdapter =
            BuscadorClienteAdapter(lista, object : BuscadorClienteAdapter.OnItemClickListener {
                override fun onItemClick(campamento: CampamentoDto?) {
                    campamento?.let { clickonItem(campamento) }
                }
            })

        recyclerCamp?.adapter = lpAdapter
    }

    fun getAllCampamentos(): List<CampamentoDto> {
        var listaCampamentos = persistencia.obtenerCampamentosConFavoritos(LoggedUser.getInstance(null).user.id)

        return listaCampamentos
    }

    fun clickonItem(campamento: CampamentoDto) {
        val intent = Intent(this@BuscadorClienteActivity, DetalleCampamentoActivity::class.java)
        intent.putExtra(CAMPAMENTO_SELECCIONADO, campamento)
        startActivity(intent)
    }

    companion object {
        val CAMPAMENTO_SELECCIONADO = "campamento_seleccionado"
    }


}
