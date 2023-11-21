package com.example.easycamp.ui

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.ui.buscadorCliente.BuscadorClienteActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso

class DetalleCampamentoActivity : AppCompatActivity() {
    var toolBarLayout: CollapsingToolbarLayout? = null
    var imagen: ImageView? = null
    var description: TextView? = null
    var campamento: CampamentoDto? = null
    var toolbar: androidx.appcompat.widget.Toolbar? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_campamento)

        val intentCampamento = intent
        campamento =
            intentCampamento.getParcelableExtra<CampamentoDto>(BuscadorClienteActivity.CAMPAMENTO_SELECCIONADO)

        if(campamento != null){
            getComponentes()
            mostrarDatos()
        }
    }

    private fun getComponentes() {
        toolbar = findViewById(R.id.toolbar)
        toolBarLayout = findViewById(R.id.toolbar_layout)
        imagen = findViewById(R.id.imgFondo)
        description = findViewById(R.id.descripcion)
    }

    private fun mostrarDatos() {
        setSupportActionBar(toolbar)
        toolBarLayout?.title = campamento?.nombre
        campamento?.let { Picasso.get().load(it.imagen).into(imagen) }
        description?.text = campamento?.descripcion
    }


}
