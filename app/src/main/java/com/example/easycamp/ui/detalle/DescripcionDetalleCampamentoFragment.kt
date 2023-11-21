package com.example.easycamp.ui.detalle

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.easycamp.R

class DescripcionDetalleCampamentoFragment : Fragment() {
    private var numeroParticipantes: Int = 0
    private var numeroApuntados: Int = 0
    private var edadMinima: Int = 0
    private var edadMaxima: Int = 0
    private var numeroMonitores: Int = 0
    private var precio: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            numeroParticipantes = it.getInt(ARG_NUMERO_PARTICIPANTES)
            numeroApuntados = it.getInt(ARG_NUMERO_APUNTADOS)
            edadMinima = it.getInt(ARG_EDAD_MINIMA)
            edadMaxima = it.getInt(ARG_EDAD_MAXIMA)
            numeroMonitores = it.getInt(ARG_NUMERO_MONITORES)
            precio = it.getDouble(ARG_PRECIO)
        }
    }

    companion object {
        val ARG_NUMERO_PARTICIPANTES = "numero de participantes"
        val ARG_NUMERO_APUNTADOS = "numero de apuntados"
        val ARG_EDAD_MINIMA = "edad minima"
        val ARG_EDAD_MAXIMA = "edad maxima"
        val ARG_NUMERO_MONITORES = "numero de monitores"
        val ARG_PRECIO = "precio"

        @JvmStatic
        fun newInstance(
            numeroParticipantes: Int,
            numeroApuntados: Int,
            edadMinima: Int,
            edadMaxima: Int,
            numeroMonitores: Int,
            precio: Double
        ) =
            DescripcionDetalleCampamentoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_NUMERO_PARTICIPANTES, numeroParticipantes)
                    putInt(ARG_NUMERO_APUNTADOS, numeroApuntados)
                    putInt(ARG_EDAD_MINIMA, edadMinima)
                    putInt(ARG_EDAD_MAXIMA, edadMaxima)
                    putInt(ARG_NUMERO_MONITORES, numeroMonitores)
                    putDouble(ARG_PRECIO, precio)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View =
            inflater.inflate(R.layout.fragment_descripcion_detalle_campamento, container, false)
        val txtNumeroApuntados = root.findViewById<TextView>(R.id.txt_numero_apuntados)
        val txtNumeroMonitores = root.findViewById<TextView>(R.id.txt_numero_monitores)
        val txtNumeroParticipantes = root.findViewById<TextView>(R.id.txt_numero_participantes)
        val txtEdadMaxima = root.findViewById<TextView>(R.id.txt_edad_maxima)
        val txtEdadMinima = root.findViewById<TextView>(R.id.txt_edad_minima)
        val txtPrecio = root.findViewById<TextView>(R.id.txt_precio)

        txtNumeroApuntados.text = "Número de apuntados: " + numeroApuntados
        txtNumeroMonitores.text = "Número de monitores: " + numeroMonitores
        txtNumeroParticipantes.text = "Número de participantes: " + numeroParticipantes
        txtEdadMaxima.text = "Edad máxima: " + edadMaxima
        txtEdadMinima.text = "Edad mínima: " + edadMinima
        txtPrecio.text = "Precio: " + precio
        return root
    }
}