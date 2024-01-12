package com.example.easycamp.ui.coordinador

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.ui.buscadorCliente.ApuntarHijosActivity
import com.example.easycamp.ui.buscadorCliente.RecyclerClienteFragment
import com.example.easycamp.ui.detalle.DescripcionDetalleCampamentoFragment
import com.example.easycamp.ui.detalle.ResumenDetalleCampamentoFragment
import com.example.easycamp.util.DBHelper
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import com.squareup.picasso.Picasso

class DetalleCampamentoCoordinador : AppCompatActivity() {

    lateinit var toolBarLayout: CollapsingToolbarLayout
    lateinit var imagen: ImageView
    var campamento: CampamentoDto? = null
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var fab: FloatingActionButton
    private lateinit var service: DBHelper

    private var navView: BottomNavigationView? = null
    private val mOnNavigationItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->

            val itemId = item.itemId
            campamento?.let { c ->
                if (itemId == R.id.navigation_resumen) {
                    val resumenFragment = ListaTrabajadoresInscritosFragment.newInstance(c)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_detalle_coordinador, resumenFragment).commit()
                    return@OnItemSelectedListener true
                }
                if (itemId == R.id.navigation_descripcion) {
                    val descripcionFragment = DescripcionDetalleCampamentoFragment.newInstance(
                        c.numero_max_participantes,
                        c.numero_apuntados,
                        c.edad_minima,
                        c.edad_maxima,
                        c.num_monitores,
                        c.precio
                    )
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_detalle_coordinador, descripcionFragment).commit()
                    return@OnItemSelectedListener true
                }
            }

            throw java.lang.IllegalStateException("Unexpected value: " + item.itemId)
        }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_campamento_coordinador)

        service = DBHelper(baseContext)

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
        campamento?.let { Picasso.get().load(it.imagen).into(imagen) }
        navView?.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_detalle_coordinador, ListaTrabajadoresInscritosFragment.newInstance(campamento!!))
            .commit()

    }
}