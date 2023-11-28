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
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.util.DBHelper


class AgregarHijoFragment : DialogFragment() {

    private lateinit var service: DBHelper
    private lateinit var usuarioActual: UserDTO

    private lateinit var  btnGuardarNuevoHijo :Button
    private lateinit var etNombreNuevoHijo :  EditText
    private lateinit var etApellidosNuevoHijo : EditText
    private lateinit var etEdadNuevoHijo :  EditText
    private lateinit var etObservacionesNuevoHijo : EditText
    private lateinit var nuevoHijo:HijoDTO
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_agregar_hijo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        service = DBHelper(requireContext())
        usuarioActual = obtenerUsuarioActual()
        btnGuardarNuevoHijo = view.findViewById(R.id.btnGuardarNuevoHijo)

        btnGuardarNuevoHijo.setOnClickListener {
            guardarNuevoHijo()
        }
    }

    private fun obtenerUsuarioActual(): UserDTO {
        return LoggedUserDTO.getInstance(null).user;
    }

    private fun guardarNuevoHijo() {
        val nombre = etNombreNuevoHijo.text.toString()
        val apellidos = etApellidosNuevoHijo.text.toString()
        val edadText = etEdadNuevoHijo.text.toString()
        val observaciones = etObservacionesNuevoHijo.text.toString()

        // Comprobar si el campo de nombre está vacío
        if (nombre.isEmpty()) {
            etNombreNuevoHijo.error = "Nombre de hijo vacío"
            etNombreNuevoHijo.requestFocus()
            return
        }

        // Comprobar si el campo de apellidos está vacío
        if (apellidos.isEmpty()) {
            etApellidosNuevoHijo.error = "Apellidos de hijo vacíos"
            etApellidosNuevoHijo.requestFocus()
            return
        }

        // Comprobar si el campo de edad está vacío
        if (edadText.isEmpty()) {
            etEdadNuevoHijo.error = "Edad de hijo vacía"
            etEdadNuevoHijo.requestFocus()
            return
        }

        // Validar que la edad sea un número entero
        val edad = try {
            edadText.toInt()
        } catch (e: NumberFormatException) {
            etEdadNuevoHijo.error = "Edad de hijo no válida"
            etEdadNuevoHijo.requestFocus()
            return
        }

        // Crear un nuevo objeto HijoDTO con los valores proporcionados
         nuevoHijo = HijoDTO(0, nombre, apellidos, edad, observaciones)



        // Aquí puedes actualizar la lista de hijos en el fragmento principal o hacer lo que sea necesario
        dismiss()  // Cerrar el diálogo después de guardar
    }

    fun getHijoDTO(): HijoDTO {
        return nuevoHijo
    }

}
