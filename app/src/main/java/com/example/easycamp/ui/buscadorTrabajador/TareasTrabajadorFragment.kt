package com.example.easycamp.ui.buscadorTrabajador

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.TareaDTO
import com.example.easycamp.util.crud.FirebaseTareaManager
import com.google.firebase.auth.FirebaseAuth



class TareasTrabajadorFragment : Fragment() {
    private lateinit var recyclerCamp: RecyclerView
    private lateinit var adapterTareas: TareasTrabajadorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_tareas_trabajador, container, false)

        recyclerCamp = view.findViewById(R.id.recycler_tareas_trabajador)
        adapterTareas = TareasTrabajadorAdapter(ArrayList(), object : TareasTrabajadorAdapter.OnItemClickListener {
            override fun onItemClick(tarea: TareaDTO?) {
                // Manejar clic en la tarea si es necesario
            }
        })

        recyclerCamp.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterTareas
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener tareas del usuario actual
        obtenerTareasDeUsuario { lista ->
            adapterTareas.actualizarListaTareas(lista)
        }
    }

    private fun obtenerTareasDeUsuario(callback: (List<TareaDTO>) -> Unit) {
        // Obtener el usuario actual
        val currentUser = FirebaseAuth.getInstance().currentUser

        currentUser?.let { user ->
            // Utilizar Firebase para obtener las tareas del usuario
            val firebaseTareaManager = FirebaseTareaManager()
            firebaseTareaManager.obtenerTareasPorUsuario(user.uid, object : FirebaseTareaManager.OnTareasReceivedListener {
                override fun onTareasReceived(tareas: List<TareaDTO>) {
                    // Llamar al callback con la lista de tareas recibida
                    callback.invoke(tareas)
                }

                override fun onError(exception: Exception) {
                    // Manejar errores según tus necesidades
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TareasTrabajadorFragment()
    }
}
