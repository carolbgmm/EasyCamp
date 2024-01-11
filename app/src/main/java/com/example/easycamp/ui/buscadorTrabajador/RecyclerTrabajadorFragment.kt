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
import com.example.easycamp.domain.CampamentoDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.ui.buscadorCliente.BuscadorClienteAdapter
import com.example.easycamp.ui.detalle.DetalleCampamentoActivity
import com.example.easycamp.util.crud.FirebaseCampamentoManager
import com.example.easycamp.util.crud.FirebaseUserManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class RecyclerTrabajadorFragment : Fragment() {
    private lateinit var recyclerCamp: RecyclerView


    private lateinit var searchBar: SearchBar
    private lateinit var searchView: SearchView
    private lateinit var appBarLayout: AppBarLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_trabajador, container, false)


        recyclerCamp= view.findViewById(R.id.recycler_campamentos_trabajador)

        searchBar = view.findViewById(R.id.search_bar_cliente)
        searchView = view.findViewById(R.id.search_view_cliente)
        appBarLayout = view.findViewById(R.id.search)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firebaseCampamentoManager = FirebaseCampamentoManager()

        firebaseCampamentoManager.obtenerCampamentos(object : FirebaseCampamentoManager.OnCampamentosObtenidosListener {
            override fun onCampamentosObtenidos(campamentos: List<CampamentoDTO>) {
                val adapterCampamentos = BuscadorTrabajadorAdapter(campamentos, object : BuscadorTrabajadorAdapter.OnItemClickListener {
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

        searchView.editText.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            searchBar.setText(searchView.text)
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

    fun clickonItem(campamento: CampamentoDTO) {
        val intent = Intent(activity, DetalleCampamentoActivity::class.java)
        intent.putExtra(CAMPAMENTO_SELECCIONADO, campamento)
        startActivity(intent)
    }

    fun getListaDeSearchView() {
        searchView?.let { searchView ->
            // Obtener el ID de usuario actual
            val firebaseUserManager = FirebaseUserManager()
            firebaseUserManager.obtenerUsuarioActual(object : FirebaseUserManager.OnUserDTOReceivedListener {
                override fun onUserDTOReceived(userDTO: UserDTO?) {
                    if (userDTO != null) {
                        val idUsuario = userDTO.id

                        // Llamar al método que obtiene los campamentos con el filtro de búsqueda
                        obtenerCampamentosCon(idUsuario, searchView.text.toString())
                    } else {
                        // Manejar el caso en el que no se pudo obtener el usuario actual
                    }
                }
            })
        }

        searchBar.visibility = View.VISIBLE
        searchBar.isVisible = true
        searchView.setVisible(false)
        searchView.visibility = View.GONE
    }

    // Método para obtener campamentos con filtro de búsqueda
    private fun obtenerCampamentosCon(idUsuario: String, filtro: String) {
        val firebaseCampamentoManager = FirebaseCampamentoManager()
        firebaseCampamentoManager.obtenerCampamentosConFiltro(idUsuario, filtro, object : FirebaseCampamentoManager.OnCampamentosConFiltroListener {
            override fun onCampamentosConFiltroObtenidos(campamentosFiltrados: List<CampamentoDTO>) {
                // Crear y asignar el adaptador con la lista de campamentos filtrados
                val adapterCampamentos = BuscadorClienteAdapter(campamentosFiltrados, object : BuscadorClienteAdapter.OnItemClickListener {
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
                // Manejar errores al obtener campamentos con filtro
            }
        })
    }
}