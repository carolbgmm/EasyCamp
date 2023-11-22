package com.example.easycamp.ui.detalle

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.ui.buscadorCliente.RecyclerClienteFragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.squareup.picasso.Picasso

class DetalleCampamentoActivity : AppCompatActivity() {
    var toolBarLayout: CollapsingToolbarLayout? = null
    var imagen: ImageView? = null
    var campamento: CampamentoDto? = null
    var toolbar: androidx.appcompat.widget.Toolbar? = null

    private var navView: BottomNavigationView? = null
    private val mOnNavigationItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->

            val itemId = item.itemId
            campamento?.let { c ->
                if (itemId == R.id.navigation_resumen) {
                    val resumenFragment = ResumenDetalleCampamentoFragment.newInstance(
                        c.descripcion!!,
                        c.fechaFinal!!,
                        c.fechaInicio!!,
                        c.ubicacion!!,
                        c.categoria!!
                    )
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_detalle, resumenFragment).commit()
                    return@OnItemSelectedListener true
                }
                if (itemId == R.id.navigation_descripcion) {
                    val descripcionFragment = DescripcionDetalleCampamentoFragment.newInstance(
                        c.numeroMaxParticipantes,
                        c.numeroApuntados,
                        c.edadMinima,
                        c.edadMaxima,
                        c.numMonitores,
                        c.precio
                    )
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_detalle, descripcionFragment).commit()
                    return@OnItemSelectedListener true
                }
            }

            throw java.lang.IllegalStateException("Unexpected value: " + item.itemId)
        }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_campamento)

        campamento =
            intent.getParcelableExtra(RecyclerClienteFragment.CAMPAMENTO_SELECCIONADO)

        campamento?.let {
            getComponentes()
            mostrarDatos()
        }


    }


    private fun getComponentes() {
        toolbar = findViewById(R.id.toolbar)
        toolBarLayout = findViewById(R.id.toolbar_layout)
        imagen = findViewById(R.id.imgFondo)
        navView = findViewById(R.id.bottom_navigation_detalle)
    }

    private fun mostrarDatos() {
        setSupportActionBar(toolbar)
        toolBarLayout?.title = campamento?.nombre!!
       // campamento?.let { Picasso.get().load(it.imagen).into(imagen) }
        navView?.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_detalle, ResumenDetalleCampamentoFragment.newInstance(
                campamento?.descripcion!!,
                campamento?.fechaFinal!!,
                campamento?.fechaInicio!!,
                campamento?.ubicacion!!,
                campamento?.categoria!!
            ))
            .commit()
    }

}
