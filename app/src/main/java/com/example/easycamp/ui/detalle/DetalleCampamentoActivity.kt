package com.example.easycamp.ui.detalle

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.ui.buscadorCliente.ApuntarHijosActivity
import com.example.easycamp.ui.buscadorCliente.RecyclerClienteFragment
import com.example.easycamp.util.DBHelper

import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import com.squareup.picasso.Picasso

class DetalleCampamentoActivity : AppCompatActivity() {
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
                    val resumenFragment = ResumenDetalleCampamentoFragment.newInstance(
                        c.descripcion!!,
                        c.fecha_final!!,
                        c.fecha_inicio!!,
                        c.ubicacion!!,
                        c.categoria!!
                    )
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_detalle, resumenFragment).commit()
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
        fab = findViewById(R.id.fab)
    }

    private fun mostrarDatos() {
        setSupportActionBar(toolbar)
        toolBarLayout?.title = campamento?.nombre!!
        campamento?.let { Picasso.get().load(it.imagen).into(imagen) }
        navView?.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_detalle, ResumenDetalleCampamentoFragment.newInstance(
                campamento?.descripcion!!,
                campamento?.fecha_final!!,
                campamento?.fecha_inicio!!,
                campamento?.ubicacion!!,
                campamento?.categoria!!
            ))
            .commit()

        val user = LoggedUserDTO.getInstance(null).user
        var yaInscrito = false

        if(user.tipoUsuario.equals("TRABAJADOR")){
            val lista =  service.obtenerInscritosDeTrabajador(user.id)

            lista.removeIf { c->
                c.id != campamento!!.id
            }
            if(!lista.isEmpty()){
                yaInscrito = true
                fab.setImageResource(R.drawable.campamento_solicitado)
            }
        }


        fab.setOnClickListener {

            if(user.tipoUsuario.equals("CLIENTE")){
                val intent = Intent(this, ApuntarHijosActivity::class.java)
                // Redirige a la página principal TRABAJADOR
                intent.putExtra(RecyclerClienteFragment.CAMPAMENTO_SELECCIONADO, campamento)
                startActivity(intent)
            } else {
                if(yaInscrito){
                    service.desInscribirUsuario(user.id, campamento!!.id)
                    fab.setImageResource(R.drawable.solicitar_campamento)
                    yaInscrito = false
                } else {
                    yaInscrito = true
                    service.inscribirUsuario(user.id, campamento!!.id)
                    fab.setImageResource(R.drawable.campamento_solicitado)
                }

            }

        }
    }

}
