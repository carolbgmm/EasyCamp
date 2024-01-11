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
import com.example.easycamp.ui.buscadorCliente.RecyclerClienteFragment
import com.example.easycamp.ui.detalle.DetalleCampamentoActivity
import com.example.easycamp.util.crud.FirebaseInscritosManager
import com.google.firebase.auth.FirebaseAuth

class InscritosTrabajadorFragment : Fragment() {

    private lateinit var recyclerCamp: RecyclerView
    private val firebaseInscritosManager = FirebaseInscritosManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inscritos_trabajador, container, false)
        recyclerCamp = view.findViewById(R.id.recycler_campamentos_inscrito)
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = InscritosTrabajadorFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener la lista de campamentos en los que está inscrito el trabajador
        val trabajadorId = FirebaseAuth.getInstance().currentUser?.uid
        trabajadorId?.let {
            firebaseInscritosManager.obtenerCampamentosInscritosDeUsuario(
                it,
                object : FirebaseInscritosManager.OnCampamentosInscritosListener {
                    override fun onCampamentosInscritosObtenidos(campamentosInscritos: List<CampamentoDTO>) {
                        // Configurar el adaptador con la lista de campamentos inscritos
                        val adapterCampamentos = BuscadorTrabajadorAdapter(
                            campamentosInscritos,
                            object : BuscadorTrabajadorAdapter.OnItemClickListener {
                                override fun onItemClick(campamento: CampamentoDTO?) {
                                    campamento?.let { clickonItem(campamento) }
                                }
                            }
                        )
                        recyclerCamp.apply {
                            setHasFixedSize(true)
                            layoutManager = LinearLayoutManager(activity)
                            adapter = adapterCampamentos
                        }
                    }

                    override fun onError(exception: Exception) {
                        // Manejar errores según tus necesidades
                    }
                }
            )
        }
    }

    private fun clickonItem(campamento: CampamentoDTO) {
        val intent = Intent(activity, DetalleCampamentoActivity::class.java)
        intent.putExtra(RecyclerClienteFragment.CAMPAMENTO_SELECCIONADO, campamento)
        startActivity(intent)
    }
}
