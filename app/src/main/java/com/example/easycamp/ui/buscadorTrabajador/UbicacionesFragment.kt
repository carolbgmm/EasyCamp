package com.example.easycamp.ui.buscadorTrabajador

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.easycamp.R
import com.example.easycamp.util.DBHelper

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UbicacionesFragment : Fragment() {

    private lateinit var posCampamento: LatLng
    private lateinit var persistencia: DBHelper

    private val callback = OnMapReadyCallback { googleMap ->
        //val sydney = LatLng(-34.0, 151.0)
        //googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        // aqui hay que obtener los campamentos a los que esta inscrito el trabajador
        // cogemos la latitud y la longuitud del campamento y el titulo

        //googleMap.addMarker(MarkerOptions().position(posCampamento).title("Marker in Sydney"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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