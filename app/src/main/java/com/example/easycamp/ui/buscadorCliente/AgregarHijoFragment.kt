package com.example.easycamp.ui.buscadorCliente



// AgregarHijoFragment.kt
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.easycamp.R
import com.example.easycamp.domain.HijoDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.util.DBHelper




class AgregarHijoFragment(
    adapter: ListaHijosAdapter,
    listaDeHijos: MutableList<HijoDTO>,
    usuarioActual: UserDTO
) : DialogFragment() {
    private var usuarioActual: UserDTO=usuarioActual
    private var adapter: ListaHijosAdapter =adapter
    private lateinit var service: DBHelper
    private lateinit var btnAceptar: Button
    private lateinit var etNombre: EditText
    private lateinit var etApellidos: EditText
    private lateinit var etEdad: EditText
    private lateinit var etObservaciones: EditText
    private var listaDeHijos: MutableList<HijoDTO> =listaDeHijos


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_agregar_hijo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        service = DBHelper(requireContext())
                btnAceptar = view.findViewById(R.id.btnAceptar)
                etNombre = view.findViewById(R.id.etNombre)
                etApellidos = view.findViewById(R.id.etApellidos)
                etEdad = view.findViewById(R.id.etEdad)
                etObservaciones = view.findViewById(R.id.etObservaciones)

                btnAceptar.setOnClickListener {
            guardarNuevoHijo()
        }
    }

    private fun guardarNuevoHijo() {
        val nombre = etNombre.text.toString()
        val apellidos = etApellidos.text.toString()
        val edadText = etEdad.text.toString()
        val observaciones = etObservaciones.text.toString()

        // Validar campos vacíos
        if (nombre.isEmpty() || apellidos.isEmpty() || edadText.isEmpty()) {
            // Manejar el error según tus necesidades
            return
        }

        // Validar que la edad sea un número entero
        val edad = try {
            edadText.toInt()
        } catch (e: NumberFormatException) {
            // Manejar el error según tus necesidades
            return
        }
        var nuevoHijo = HijoDTO(0, nombre, apellidos, edad, observaciones)

        val nuevoHijoId = service.crearHijo(nuevoHijo, usuarioActual.id)
        nuevoHijo.id = nuevoHijoId
        listaDeHijos.add(nuevoHijo)
        adapter.submitList(listaDeHijos)


        dismiss()
    }


}

