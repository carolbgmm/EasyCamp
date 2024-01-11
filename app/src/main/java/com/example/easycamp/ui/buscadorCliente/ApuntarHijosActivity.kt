package com.example.easycamp.ui.buscadorCliente

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDTO
import com.example.easycamp.domain.HijoDTO
import com.example.easycamp.util.crud.FirebaseHijoManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseException

class ApuntarHijosActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var listaDeHijos: MutableList<HijoDTO>
    private var campamento: CampamentoDTO? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apuntar_hijos)

        recyclerView = findViewById(R.id.recyclerViewHijos)

        campamento = intent.getParcelableExtra(RecyclerClienteFragment.CAMPAMENTO_SELECCIONADO)

        mAuth = FirebaseAuth.getInstance()

        // Obtén el usuario actual
        mAuth.currentUser?.let { firebaseUser ->
            val firebaseHijoManager = FirebaseHijoManager()

            // Utiliza el ID del usuario actual para obtener la lista de hijos
            firebaseHijoManager.obtenerHijosPorIdPadre(firebaseUser.uid, object : FirebaseHijoManager.OnHijosReceivedListener {
                override fun onHijosReceived(hijos: List<HijoDTO>) {
                    // Ahora 'hijos' contiene la lista de hijos del padre
                    listaDeHijos = hijos.toMutableList()

                    // Filtra la lista de hijos según la edad del campamento
                    campamento?.let { c ->
                        listaDeHijos.removeIf {
                            it.edad < c.edad_minima || it.edad > c.edad_maxima
                        }
                    }

                    val layoutManager = LinearLayoutManager(this@ApuntarHijosActivity)
                    recyclerView.layoutManager = layoutManager

                    val adapter = ListaApuntarHijosAdapter(campamento!!)
                    recyclerView.adapter = adapter
                    adapter.submitList(listaDeHijos)
                }

                override fun onError(toException: DatabaseException?) {
                    // Manejar errores según tus necesidades
                    // Log.e("MiApp", "Error al obtener la lista de hijos", toException)
                }
            })
        }
    }
}
