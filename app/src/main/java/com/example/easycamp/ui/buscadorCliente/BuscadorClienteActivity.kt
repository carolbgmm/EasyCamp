package com.example.easycamp.ui.buscadorCliente

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.easycamp.R
import com.example.easycamp.domain.Campamento
import com.example.easycamp.ui.DetalleCampamentoActivity

class BuscadorClienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscador_cliente)

        var lpAdapter =
            BuscadorClienteAdapter(listOf(), object : BuscadorClienteAdapter.OnItemClickListener {
                override fun onItemClick(campamento: Campamento?) {
                    campamento?.let { clickonItem(campamento) }
                }
            })
    }

    fun clickonItem(campamento: Campamento) {
        val intent = Intent(this@BuscadorClienteActivity, DetalleCampamentoActivity::class.java)
        intent.putExtra(CAMPAMENTO_SELECCIONADO, campamento)
        startActivity(intent)
    }

    companion object {
        val CAMPAMENTO_SELECCIONADO = "campamento_seleccionado"
    }


}
