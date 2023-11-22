package com.example.easycamp.ui.buscadorTrabajador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.easycamp.R
import com.example.easycamp.ui.buscadorCliente.CuentaClienteFragment
import com.example.easycamp.ui.buscadorCliente.RecyclerClienteFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class BuscadorTrabajadorActivity : AppCompatActivity() {
    private var navView: BottomNavigationView? = null
    private val mOnNavigationItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->

            val itemId = item.itemId

            if (itemId == R.id.buscador_trabajador) {
                val recyclerFragment = RecyclerTrabajadorFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_trabajador, recyclerFragment).commit()
                return@OnItemSelectedListener true
            }
            if (itemId == R.id.tareas_trabajador) {
                val tareasTrabajadorFragment = TareasTrabajadorFragment.newInstance("", "")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_trabajador, tareasTrabajadorFragment).commit()
                return@OnItemSelectedListener true
            }
            if (itemId == R.id.herramientas_trabajador) {
                val herramientasFragment = HerramientasFragment.newInstance("", "")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_trabajador, herramientasFragment).commit()
                return@OnItemSelectedListener true
            }
            if (itemId == R.id.perfil_trabajador) {
                val cuentaTrabajadorTFragment = CuentaTrabajadorFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_trabajador, cuentaTrabajadorTFragment).commit()
                return@OnItemSelectedListener true

            }
            throw java.lang.IllegalStateException("Unexpected value: " + item.itemId)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscador_trabajador)
        navView = findViewById(R.id.bottom_navigation_trabajador)
        navView?.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_trabajador,  RecyclerClienteFragment.newInstance())
            .commit()

    }
}