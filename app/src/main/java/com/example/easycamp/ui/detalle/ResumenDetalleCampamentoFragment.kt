package com.example.easycamp.ui.detalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.easycamp.R

class ResumenDetalleCampamentoFragment : Fragment() {
    private var descripcion: String? = null
    private var fechaFin: String? = null
    private var fechaInicio: String? = null
    private var ubicacion: String? = null
    private var categoria: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            descripcion = it.getString(ARG_DESCRIPCION)
            fechaFin = it.getString(ARG_FECHA_FIN)
            fechaInicio = it.getString(ARG_FECHA_INICIO)
            ubicacion = it.getString(ARG_UBICACION)
            categoria = it.getString(ARG_CATEGORIA)
        }
    }

    companion object {
        private const val ARG_DESCRIPCION = "descripcion"
        private const val ARG_FECHA_FIN = "fecha fin"
        private const val ARG_FECHA_INICIO = "fecha inicio"
        private const val ARG_UBICACION = "ubicacion"
        private const val ARG_CATEGORIA = "categoria"

        @JvmStatic
        fun newInstance(
            descripcion: String,
            fechaFin: String,
            fechaInicio: String,
            ubicacion: String,
            categoria: String
        ) =
            ResumenDetalleCampamentoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DESCRIPCION, descripcion)
                    putString(ARG_FECHA_FIN, fechaFin)
                    putString(ARG_FECHA_INICIO, fechaInicio)
                    putString(ARG_UBICACION, ubicacion)
                    putString(ARG_CATEGORIA, categoria)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root: View = inflater.inflate(R.layout.fragment_resumen_detalle_campamento, container, false)
        val txtDescripcion = root.findViewById<TextView>(R.id.txt_descripcion)
        val txtFechaFin = root.findViewById<TextView>(R.id.txt_fecha_final)
        val txtFechaInicio = root.findViewById<TextView>(R.id.txt_fecha_inicio)
        val txtUbicacion = root.findViewById<TextView>(R.id.txt_ubicacion)
        val txtCategoria = root.findViewById<TextView>(R.id.txt_categoria)

        txtDescripcion.setText("Descripción: " + descripcion)
        txtFechaFin.setText("Fecha de finalización: " + fechaFin)
        txtFechaInicio.setText("Fecha de inicio: "+ fechaInicio)
        txtUbicacion.setText("Ubicación: " + ubicacion)
        txtCategoria.setText("Categoría: " + categoria)
        return root
    }
}