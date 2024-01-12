package com.example.easycamp.ui.coordinador

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.easycamp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class CoordinadorActivity : AppCompatActivity() {
    private lateinit var navView: BottomNavigationView

    private val mOnNavigationItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->
            val itemId = item.itemId

            if (itemId == R.id.perfil_coordinador) {
                val perfilFragment = PerfilCoordinadorFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_coordinador, perfilFragment).commit()
                return@OnItemSelectedListener true
            } else if (itemId == R.id.crear_campamento) {
                val crearCampamentoFragment = ListaCampamentosFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_coordinador, crearCampamentoFragment).commit()
                return@OnItemSelectedListener true
            }

            throw IllegalStateException("Unexpected value: " + item.itemId)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinador2)

        navView = findViewById(R.id.bottom_navigation_coordinador)
        navView.setOnItemSelectedListener(mOnNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_coordinador, ListaCampamentosFragment.newInstance())
            .commit()
    }
}
