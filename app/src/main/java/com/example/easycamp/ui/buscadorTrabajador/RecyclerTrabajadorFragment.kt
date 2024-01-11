package com.example.easycamp.ui.buscadorTrabajador

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.ui.buscadorCliente.BuscadorClienteAdapter
import com.example.easycamp.ui.detalle.DetalleCampamentoActivity
import com.example.easycamp.util.DBHelper
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class RecyclerTrabajadorFragment : Fragment() {
    private lateinit var recyclerCamp: RecyclerView
    private lateinit var persistencia: DBHelper

    private lateinit var searchBar: SearchBar
    private lateinit var searchView: SearchView
    private lateinit var appBarLayout: AppBarLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_trabajador, container, false)

        persistencia = DBHelper.getInstance(context)
        recyclerCamp= view.findViewById(R.id.recycler_campamentos_trabajador)

        searchBar = view.findViewById(R.id.search_bar_cliente)
        searchView = view.findViewById(R.id.search_view_cliente)
        appBarLayout = view.findViewById(R.id.search)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lista = persistencia.obtenerCampamentosConFavoritos(LoggedUserDTO.getInstance(null).user.id)
        val adapterCampamentos = BuscadorTrabajadorAdapter(lista, object : BuscadorTrabajadorAdapter.OnItemClickListener {
            override fun onItemClick(campamento: CampamentoDto?) {
                campamento?.let { clickonItem(campamento) }
            }
        })
        recyclerCamp.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterCampamentos

        }

        searchView.setupWithSearchBar(searchBar)

        searchBar.setOnClickListener {
            searchBar.visibility = View.GONE
            searchBar.isVisible = false
            searchView.setVisible(true)
            searchView.visibility = View.VISIBLE
        }

        searchView.setOnKeyListener { view, i, keyEvent ->
            searchBar.setText(searchView.text.toString())
            false
        }

        searchView.editText
            .setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
                searchBar.setText(searchView.getText())
                getListaDeSearchView()
                false
            }


    }

    companion object {
        val CAMPAMENTO_SELECCIONADO = "campamento_seleccionado"
        @JvmStatic
        fun newInstance() =
            RecyclerTrabajadorFragment()
    }

    fun clickonItem(campamento: CampamentoDto) {
        val intent = Intent(activity, DetalleCampamentoActivity::class.java)
        intent.putExtra(CAMPAMENTO_SELECCIONADO, campamento)
        startActivity(intent)
    }

    fun getListaDeSearchView(){
        searchView?.let {
            val lista = persistencia.obtenerCampamentosCon(LoggedUserDTO.getInstance(null).user.id, it.text.toString())
            val adapterCampamentos = BuscadorClienteAdapter(lista, object : BuscadorClienteAdapter.OnItemClickListener {
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

        searchBar.visibility = View.VISIBLE
        searchBar.isVisible = true
        searchView.setVisible(false)
        searchView.visibility = View.GONE

    }
}