package com.example.easycamp.ui.buscadorCliente

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
import com.example.easycamp.ui.detalle.DetalleCampamentoActivity
import com.example.easycamp.util.crud.FirebaseInscritosManager
import com.example.easycamp.util.crud.FirebaseUserManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InscritosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InscritosFragment : Fragment() {
    private lateinit var recyclerCamp: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_inscritos, container, false)


        recyclerCamp= view.findViewById(R.id.recycler_campamentos_inscrito)
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            InscritosFragment().apply {
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inscritosManager = FirebaseInscritosManager()

        // Obtener el usuario actual
        val firebaseUserManager = FirebaseUserManager()
        firebaseUserManager.obtenerUsuarioActual(object : FirebaseUserManager.OnUserDTOReceivedListener {
            override fun onUserDTOReceived(userDTO: UserDTO?) {
                // Verificar si se obtuvo el usuario correctamente
                if (userDTO != null) {
                    // Obtener el idPadre del usuario actual
                    val idPadre = userDTO.getId()

                    // Llamar al método que obtiene los inscritos usando el idPadre
                    inscritosManager.obtenerCampamentosInscritosDeUsuario(idPadre, object : FirebaseInscritosManager.OnCampamentosInscritosListener {
                        override fun onCampamentosInscritosObtenidos(campamentosInscritos: List<CampamentoDTO>) {
                            // La lista de campamentos inscritos se ha recibido correctamente

                            // Crear y asignar el adaptador con la lista obtenida
                            val adapterCampamentos =
                                BuscadorClienteAdapter(campamentosInscritos, object : BuscadorClienteAdapter.OnItemClickListener {
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
                            // Por ejemplo, mostrar un mensaje de error o redirigir a la pantalla de inicio
                        }
                    })
                } else {
                    // No se pudo obtener el usuario actual, manejar el error según tus necesidades
                    // Por ejemplo, redirigir a la pantalla de inicio de sesión
                }
            }
        })
    }


    fun clickonItem(campamento: CampamentoDTO) {
        val intent = Intent(activity, DetalleCampamentoActivity::class.java)
        intent.putExtra(RecyclerClienteFragment.CAMPAMENTO_SELECCIONADO, campamento)
        startActivity(intent)
    }
}