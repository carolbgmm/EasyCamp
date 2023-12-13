package com.example.easycamp.ui.buscadorCliente

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.easycamp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class BuscadorClienteActivity : AppCompatActivity() {
    private var navView: BottomNavigationView? = null
    private val mOnNavigationItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->

            val itemId = item.itemId

            if (itemId == R.id.navigation_campamentos) {
                val recyclerFragment = RecyclerClienteFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_cliente, recyclerFragment).commit()
                return@OnItemSelectedListener true
            }
            if (itemId == R.id.navigation_cuenta) {
                val perfilFragment = CuentaClienteFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_cliente, perfilFragment).commit()
                return@OnItemSelectedListener true
            }
            if (itemId == R.id.navigation_inscritos) {
                val inscritosFragment = InscritosFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_cliente, inscritosFragment).commit()
                return@OnItemSelectedListener true
            }
            throw java.lang.IllegalStateException("Unexpected value: " + item.itemId)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscador_cliente)
        navView = findViewById(R.id.bottom_navigation_cliente)
        navView?.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_cliente,  RecyclerClienteFragment.newInstance())
            .commit()

    }


}
