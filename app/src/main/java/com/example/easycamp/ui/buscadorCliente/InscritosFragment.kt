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
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.ui.detalle.DetalleCampamentoActivity
import com.example.easycamp.util.DBHelper

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
    private lateinit var persistencia: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_inscritos, container, false)

        persistencia = DBHelper.getInstance(context)
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
        val lista =
            persistencia.obtenerInscritosDeUsuario(LoggedUserDTO.getInstance(null).user.id)
        val adapterCampamentos =
            BuscadorClienteAdapter(lista, object : BuscadorClienteAdapter.OnItemClickListener {
                override fun onItemClick(campamento: CampamentoDto?) {
                    campamento?.let { clickonItem(campamento) }
                }
            })
        recyclerCamp.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterCampamentos

        }
    }
    fun clickonItem(campamento: CampamentoDto) {
        val intent = Intent(activity, DetalleCampamentoActivity::class.java)
        intent.putExtra(RecyclerClienteFragment.CAMPAMENTO_SELECCIONADO, campamento)
        startActivity(intent)
    }
}