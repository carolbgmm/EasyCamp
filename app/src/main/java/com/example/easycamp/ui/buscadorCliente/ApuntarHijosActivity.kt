package com.example.easycamp.ui.buscadorCliente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.HijoDTO
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.util.DBHelper

class ApuntarHijosActivity : AppCompatActivity() {

    private lateinit var service: DBHelper
    private lateinit var usuarioActual: UserDTO
    private lateinit var listaDeHijos: MutableList<HijoDTO>

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregarHijo: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apuntar_hijos)

        recyclerView = findViewById(R.id.recyclerViewHijos)

        service = DBHelper(baseContext)
        listaDeHijos = service.obtenerHijosPorUsuario(LoggedUserDTO.getInstance(null).user.id).toMutableList()
        Log.d("MiApp", "Se cargo la lista de hijos ")

        val adapter = ListaApuntarHijosAdapter()
        recyclerView.adapter = adapter
        adapter.submitList(listaDeHijos)
    }
}