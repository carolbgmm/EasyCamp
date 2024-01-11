package com.example.easycamp.ui.buscadorCliente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDTO
import com.example.easycamp.domain.HijoDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.util.crud.FirebaseHijoManager
import com.example.easycamp.util.crud.FirebaseInscritosManager
import com.example.easycamp.util.crud.FirebaseUserManager
import com.google.firebase.database.DatabaseException

class ApuntarHijosActivity : AppCompatActivity() {


    private lateinit var listaDeHijos: MutableList<HijoDTO>
    private var campamento: CampamentoDTO? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apuntar_hijos)

        recyclerView = findViewById(R.id.recyclerViewHijos)

        campamento =
            intent.getParcelableExtra(RecyclerClienteFragment.CAMPAMENTO_SELECCIONADO)


        val firebaseUserManager = FirebaseUserManager()
        val firebaseHijoManager = FirebaseHijoManager()

// Obtén el usuario actual
        firebaseUserManager.obtenerUsuarioActual(object : FirebaseUserManager.OnUserDTOReceivedListener {
            override fun onUserDTOReceived(usuarioRegistrado: UserDTO?) {
                if (usuarioRegistrado != null) {
                    // Usuario actual obtenido con éxito

                    // Utiliza el ID del usuario actual para obtener la lista de hijos
                    firebaseHijoManager.obtenerHijosPorIdPadre(usuarioRegistrado.id, object : FirebaseHijoManager.OnHijosReceivedListener {
                        override fun onHijosReceived(hijos: List<HijoDTO>) {
                            // Ahora 'hijos' contiene la lista de hijos del padre
                            listaDeHijos = hijos.toMutableList()
                        }

                        override fun onError(toException: DatabaseException?) {
                            TODO("Not yet implemented")
                        }
                    })
                } else {
                    // No se pudo obtener el usuario actual
                }
            }
        })
        Log.d("MiApp", "Se cargo la lista de hijos ")

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager



        val adapter = ListaApuntarHijosAdapter(ListaApuntarHijosAdapter.OnClickListener {
            fun onClick(item: HijoDTO?) {
                campamento?.let { clickonItem(item) }
            }
        })
        recyclerView.adapter = adapter
        adapter.submitList(listaDeHijos)


    }

    fun clickonItem(hijo: HijoDTO?) {
        hijo?.let { h ->
            val firebaseUserManager = FirebaseUserManager()
            firebaseUserManager.obtenerUsuarioActual(object : FirebaseUserManager.OnUserDTOReceivedListener {
                override fun onUserDTOReceived(userDTO: UserDTO?) {
                    userDTO?.let { user ->
                        val firebaseInscritosManager = FirebaseInscritosManager()

                        // Inscribir el hijo al campamento utilizando FirebaseInscritosManager
                        firebaseInscritosManager.inscribirHijoAlCampamento(h.id, campamento?.id,user.id, object : FirebaseInscritosManager.OnInscripcionCompletadaListener {
                            override fun onInscripcionCompletada() {
                                // La inscripción se ha completado con éxito
                                // Puedes realizar alguna acción adicional si es necesario
                            }
                        })
                    }
                }
            })
        }
    }



}