package com.example.easycamp.ui.buscadorCliente

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.util.DBHelper
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
        val service = DBHelper(baseContext)
        //service.agregarCampamento(CampamentoDto(1L,"Aventura Extrema", "Una experiencia emocionante llena de desafíos.", "2023-06-15","2023-06-30",30, 0,"Montañas Peligrosas",12,18,6,299.99,"Aventura","https://picsum.photos/id/17/367/267", 40.262341,-4.976861))
        navView = findViewById(R.id.bottom_navigation_cliente)
        navView?.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_cliente,  RecyclerClienteFragment.newInstance())
            .commit()

    }


}
