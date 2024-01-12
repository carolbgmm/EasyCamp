package com.example.easycamp.ui.coordinador
// AgregarCampamentoFragment.kt


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.util.DBHelper

class AgregarCampamentoFragment : DialogFragment() {

    private lateinit var service: DBHelper
    private lateinit var btnAceptar: Button
    private lateinit var etNombre: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var etFechaInicio: EditText
    private lateinit var etFechaFinal: EditText
    private lateinit var etNumeroMaxParticipantes: EditText
    private lateinit var etUbicacion: EditText
    private lateinit var etEdadMinima: EditText
    private lateinit var etEdadMaxima: EditText
    private lateinit var etNumMonitores: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etCategoria: EditText
    private lateinit var etImagen: EditText
    private lateinit var etLatitud: EditText
    private lateinit var etLongitud: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_agregar_campamento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        service = DBHelper(requireContext())
        btnAceptar = view.findViewById(R.id.btnAceptarCampamento)
        etNombre = view.findViewById(R.id.etNombreCampamento)
        etDescripcion = view.findViewById(R.id.etDescripcionCampamento)
        etFechaInicio = view.findViewById(R.id.etFechaInicioCampamento)
        etFechaFinal = view.findViewById(R.id.etFechaFinalCampamento)
        etNumeroMaxParticipantes = view.findViewById(R.id.etNumeroMaxParticipantesCampamento)
        etUbicacion = view.findViewById(R.id.etUbicacionCampamento)
        etEdadMinima = view.findViewById(R.id.etEdadMinimaCampamento)
        etEdadMaxima = view.findViewById(R.id.etEdadMaximaCampamento)
        etNumMonitores = view.findViewById(R.id.etNumMonitoresCampamento)
        etPrecio = view.findViewById(R.id.etPrecioCampamento)
        etCategoria = view.findViewById(R.id.etCategoriaCampamento)
        etImagen = view.findViewById(R.id.etImagenCampamento)
        etLatitud = view.findViewById(R.id.etLatitudCampamento)
        etLongitud = view.findViewById(R.id.etLongitudCampamento)

        btnAceptar.setOnClickListener {
            guardarNuevoCampamento()
        }
    }

    private fun guardarNuevoCampamento() {
        val nombre = etNombre.text.toString()
        val descripcion = etDescripcion.text.toString()
        val fechaInicio = etFechaInicio.text.toString()
        val fechaFinal = etFechaFinal.text.toString()
        val numeroMaxParticipantesText = etNumeroMaxParticipantes.text.toString()
        val ubicacion = etUbicacion.text.toString()
        val edadMinimaText = etEdadMinima.text.toString()
        val edadMaximaText = etEdadMaxima.text.toString()
        val numMonitoresText = etNumMonitores.text.toString()
        val precioText = etPrecio.text.toString()
        val categoria = etCategoria.text.toString()
        val imagen = etImagen.text.toString()
        val latitudText = etLatitud.text.toString()
        val longitudText = etLongitud.text.toString()

        // Validar campos vacíos
        if (nombre.isEmpty() || descripcion.isEmpty() || fechaInicio.isEmpty() ||
            fechaFinal.isEmpty() || numeroMaxParticipantesText.isEmpty() ||
            ubicacion.isEmpty() || edadMinimaText.isEmpty() || edadMaximaText.isEmpty() ||
            numMonitoresText.isEmpty() || precioText.isEmpty() || categoria.isEmpty() ||
            imagen.isEmpty() || latitudText.isEmpty() || longitudText.isEmpty()
        ) {
            // Manejar el error según tus necesidades
            return
        }

        // Validar que los campos numéricos sean números enteros o decimales según corresponda
        val numeroMaxParticipantes = try {
            numeroMaxParticipantesText.toInt()
        } catch (e: NumberFormatException) {
            // Manejar el error según tus necesidades
            return
        }

        val edadMinima = try {
            edadMinimaText.toInt()
        } catch (e: NumberFormatException) {
            // Manejar el error según tus necesidades
            return
        }

        val edadMaxima = try {
            edadMaximaText.toInt()
        } catch (e: NumberFormatException) {
            // Manejar el error según tus necesidades
            return
        }

        val numMonitores = try {
            numMonitoresText.toInt()
        } catch (e: NumberFormatException) {
            // Manejar el error según tus necesidades
            return
        }

        val precio = try {
            precioText.toDouble()
        } catch (e: NumberFormatException) {
            // Manejar el error según tus necesidades
            return
        }

        val latitud = try {
            latitudText.toFloat()
        } catch (e: NumberFormatException) {
            // Manejar el error según tus necesidades
            return
        }

        val longitud = try {
            longitudText.toFloat()
        } catch (e: NumberFormatException) {
            // Manejar el error según tus necesidades
            return
        }

        // Crea un nuevo objeto CampamentoDto con los valores ingresados
        val nuevoCampamento = CampamentoDto(
            0,
            nombre,
            descripcion,
            fechaInicio,
            fechaFinal,
            numeroMaxParticipantes,
            0,
            ubicacion,
            edadMinima,
            edadMaxima,
            numMonitores,
            precio,
            categoria,
            imagen,
            false, // Por defecto se puede establecer como falso
            latitud,
            longitud,
            LoggedUserDTO.getInstance(null).user.id
        )


         service.crearCampamento(nuevoCampamento)

        dismiss()
    }
}
