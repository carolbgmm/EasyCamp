package com.example.easycamp.ui.buscadorCliente

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.ui.DetalleCampamentoActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class BuscadorClienteActivity : AppCompatActivity() {

    var recyclerCamp: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscador_cliente)

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
        var listaCampamentos = arrayListOf<CampamentoDto>()
        try {
            val raw: InputStream = resources.openRawResource(R.raw.campamentos)
            val b = BufferedReader(InputStreamReader(raw, "UTF8"))
            var line = b.readLine()
            while (line != null) {
                val valores = line.split(";".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val campamentoDto = CampamentoDto(valores[0], valores[1], valores[2])
                listaCampamentos.add(campamentoDto)
                line = b.readLine()
            }
            b.close()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
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
