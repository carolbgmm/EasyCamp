package com.example.easycamp.ui.buscadorCliente

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.HijoDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.util.crud.FirebaseHijoManager
import com.example.easycamp.util.crud.FirebaseUserManager
import com.google.firebase.database.DatabaseException

class ListaHijosFragment : Fragment() {

    private lateinit var usuarioActual: UserDTO
    private lateinit var listaDeHijos: MutableList<HijoDTO>

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregarHijo: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_hijos, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewHijos)
        btnAgregarHijo = view.findViewById(R.id.btnAgregarHijo)


        usuarioActual = obtenerUsuarioActual()


        val firebaseHijoManager = FirebaseHijoManager()

// Obtener el usuario actual
        val firebaseUserManager = FirebaseUserManager()
        firebaseUserManager.obtenerUsuarioActual(object : FirebaseUserManager.OnUserDTOReceivedListener {
            override fun onUserDTOReceived(userDTO: UserDTO?) {
                // Verificar si se obtuvo el usuario correctamente
                if (userDTO != null) {
                    // Obtener el idPadre del usuario actual
                    val idPadre = userDTO.getId()

                    // Llamar al método que obtiene los hijos usando el idPadre
                    firebaseHijoManager.obtenerHijosPorUsuario(idPadre, object : FirebaseHijoManager.OnHijosReceivedListener {
                        override fun onHijosReceived(hijos: List<HijoDTO>) {
                            listaDeHijos = hijos.toMutableList()
                        }

                        override fun onError(toException: DatabaseException?) {

                        }


                    })
                } else {
                    // No se pudo obtener el usuario actual, manejar el error según tus necesidades
                }
            }
        })

        Log.d("MiApp", "Se cargo la lista de hijos ")

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        val adapter = ListaHijosAdapter()
        recyclerView.adapter = adapter
        adapter.submitList(listaDeHijos)

        btnAgregarHijo.setOnClickListener {

            Log.d("MiApp", "Tamaño de listaDeHijos antes de agregar: ${listaDeHijos.size}")

            val agregarHijoFragment = AgregarHijoFragment(adapter,listaDeHijos,usuarioActual)
            agregarHijoFragment.show(requireFragmentManager(), "AgregarHijoFragment")




            Log.d("MiApp", "Tamaño de listaDeHijos después de agregar: ${listaDeHijos.size}")

            adapter.notifyDataSetChanged()
        }

        return view
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
        fun newInstance(): ListaHijosFragment {
            return ListaHijosFragment()
        }
    }
}
