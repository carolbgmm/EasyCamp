package com.example.easycamp.ui.buscadorTrabajador

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.ui.buscadorCliente.BuscadorClienteAdapter
import com.example.easycamp.ui.detalle.DetalleAsistentesActivity
import com.example.easycamp.util.crud.FirebaseInscritosManager
import com.example.easycamp.util.crud.FirebaseUserManager


class ListaAsistentesFragment : Fragment() {

    private lateinit var usuarioActual: UserDTO

    private lateinit var recyclerCamp: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lista_asistentes, container, false)

        usuarioActual = obtenerUsuarioActual()

        recyclerCamp = view.findViewById(R.id.recycler_campamentos_trabajador)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firebaseUserManager = FirebaseUserManager()
        val firebaseInscritosManager = FirebaseInscritosManager()

        // Obtener el usuario actual
        firebaseUserManager.obtenerUsuarioActual(object : FirebaseUserManager.OnUserDTOReceivedListener {
            override fun onUserDTOReceived(userDTO: UserDTO?) {
                // Verificar si se obtuvo el usuario correctamente
                if (userDTO != null) {
                    // Obtener el id del usuario actual
                    val usuarioID = userDTO.getId()

                    // Llamar al método que obtiene los campamentos inscritos
                    firebaseInscritosManager.obtenerCampamentosInscritosDeUsuario(usuarioID, object : FirebaseInscritosManager.OnCampamentosInscritosListener {
                        override fun onCampamentosInscritosObtenidos(campamentosInscritos: List<CampamentoDTO>) {
                            // La lista de campamentos inscritos se ha recibido correctamente

                            // Realizar acciones adicionales con la lista de campamentos inscritos
                            // Por ejemplo, actualizar la interfaz de usuario si es necesario

                            // Crear y asignar el adaptador con la lista obtenida
                            val adapterCampamentos = BuscadorClienteAdapter(campamentosInscritos, object : BuscadorClienteAdapter.OnItemClickListener {
                                override fun onItemClick(campamento: CampamentoDTO?) {
                                    campamento?.let { clickonItem(campamento) }
                                }
                            })

                            recyclerCamp.apply {
                                setHasFixedSize(true)
                                layoutManager = LinearLayoutManager(activity)
                                adapter = adapterCampamentos
                            }
                        }

                        override fun onError(exception: Exception) {
                            // Manejar el error según tus necesidades
                        }
                    })
                } else {
                    // No se pudo obtener el usuario actual, manejar el error según tus necesidades
                    // Por ejemplo, redirigir a la pantalla de inicio de sesión
                }
            }
        })
    }

    private fun obtenerUsuarioActual(): UserDTO {
        val firebaseUserManager = FirebaseUserManager()
        var usuarioActual: UserDTO? = null

        firebaseUserManager.obtenerUsuarioActual(object : FirebaseUserManager.OnUserDTOReceivedListener {
            override fun onUserDTOReceived(userDTO: UserDTO?) {
                userDTO?.let {
                    usuarioActual = it
                }
            }
        })

        return usuarioActual ?: throw IllegalStateException("No se pudo obtener el usuario actual")
    }


    companion object {
        //val CAMPAMENTO_SELECCIONADO = "campamento_seleccionado"
        fun newInstance(): ListaAsistentesFragment{
            return ListaAsistentesFragment()
        }
    }

    fun clickonItem(campamento: CampamentoDTO) {
        val intent = Intent(activity, DetalleAsistentesActivity::class.java)
        intent.putExtra(RecyclerTrabajadorFragment.CAMPAMENTO_SELECCIONADO, campamento)
        startActivity(intent)
    }


}