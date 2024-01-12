package com.example.easycamp.ui.buscadorTrabajador

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.easycamp.R
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.util.DBHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UbicacionesFragment : Fragment() {

    private lateinit var persistencia: DBHelper


    private val callback = OnMapReadyCallback { googleMap ->

        val lista = persistencia.obtenerAceptadosDeTrabajador(LoggedUserDTO.getInstance(null).user.id)
        val posInit = LatLng(40.392609, -3.680264)

        googleMap.getUiSettings().setZoomControlsEnabled(true);

        //Log.i("AYUDA", lista.size.toString())
        lista.forEach {
            val pos = LatLng(it.latitud.toDouble(), it.longuitud.toDouble())
            //Log.i("AYUDA", it.toString())

            googleMap.addMarker(MarkerOptions().position(pos).title(it.nombre))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posInit, 5F))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        persistencia = DBHelper.getInstance(context)
        return inflater.inflate(R.layout.fragment_ubicaciones, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


    }

    companion object {
        @JvmStatic
        fun newInstance() =
            UbicacionesFragment()
    }
}