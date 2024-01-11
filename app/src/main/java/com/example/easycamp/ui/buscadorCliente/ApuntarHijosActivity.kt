package com.example.easycamp.ui.buscadorCliente

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.domain.HijoDTO
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.ui.detalle.DetalleCampamentoActivity
import com.example.easycamp.util.crud.FirebaseHijoManager
import com.example.easycamp.util.crud.FirebaseInscritosManager
import com.example.easycamp.util.crud.FirebaseUserManager
import com.google.firebase.database.DatabaseException
import com.google.firebase.auth.FirebaseAuth

class ApuntarHijosActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var listaDeHijos: MutableList<HijoDTO>
    private var campamento: CampamentoDto? = null
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

                    val adapter = ListaApuntarHijosAdapter(object : ListaApuntarHijosAdapter.OnClickListener {
                        override fun onClick(item: HijoDTO?) {
                            campamento?.let { clickonItem(item) }
                        }
                    })
                    recyclerView.adapter = adapter
                    adapter.submitList(listaDeHijos)
                }

                override fun onError(toException: DatabaseException?) {
                    // Manejar errores según tus necesidades
                    Log.e("MiApp", "Error al obtener la lista de hijos", toException)
                }
            })
        }
    }

    private fun clickonItem(hijo: HijoDTO?) {
        hijo?.let { h ->
            mAuth.currentUser?.let { firebaseUser ->
                val firebaseInscritosManager = FirebaseInscritosManager()

                // Inscribir el hijo al campamento utilizando FirebaseInscritosManager
                firebaseInscritosManager.inscribirHijoAlCampamento(h.id, campamento?.id, firebaseUser.uid, object : FirebaseInscritosManager.OnInscripcionCompletadaListener {
                    override fun onInscripcionCompletada() {
                        // La inscripción se ha completado con éxito
                        // Puedes realizar alguna acción adicional si es necesario
                    }
                })
            }
        }
    }
}
