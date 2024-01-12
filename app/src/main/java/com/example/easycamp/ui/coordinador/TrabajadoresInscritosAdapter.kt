package com.example.easycamp.ui.coordinador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.domain.HijoDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.util.DBHelper

class TrabajadoresInscritosAdapter(var campamentoDto: CampamentoDto) : ListAdapter<UserDTO, TrabajadoresInscritosAdapter.TrabajadorViewHolder>(UserDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrabajadorViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_trabajador, parent, false)
            return TrabajadorViewHolder(view)
        }

        override fun onBindViewHolder(holder: TrabajadorViewHolder, position: Int) {
            val hijo = getItem(position)
            holder.bind(hijo, campamentoDto)
        }

        class TrabajadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val tvNombreTrabajador: TextView = itemView.findViewById(R.id.tvNombreTrabajador)
            private val tvApellidosTrabajador: TextView = itemView.findViewById(R.id.tvApellidosTrabajador)
            private val tvEdadTrabajador: TextView = itemView.findViewById(R.id.tvEdadTrabajador)
            private val tvNombreUsuarioTrabajador: TextView = itemView.findViewById(R.id.tvNombreUsuarioTrabajador)
            private val btnAceptarTrabajador: ImageButton = itemView.findViewById(R.id.btnAceptarTrabajador)
            private val btnDenegarTrabajador: ImageButton = itemView.findViewById(R.id.btnDenegarTrabajador)
            private val imagenResultado: ImageView = itemView.findViewById(R.id.imagenResultado)


            fun bind(user: UserDTO, campamentoDto: CampamentoDto) {
                tvNombreTrabajador.text = user.nombre
                tvApellidosTrabajador.text = user.apellidos
                tvEdadTrabajador.text = "Edad: ${user.edad}"
                tvNombreUsuarioTrabajador.text = user.nombreUsuario
                val service = DBHelper(itemView.context)
                btnAceptarTrabajador.setOnClickListener {
                    service.aceptarTrabajador(user.id, campamentoDto.id)
                    imagenResultado.setImageResource(R.drawable.trabajador_aprobado)
                }
                btnDenegarTrabajador.setOnClickListener {
                    service.rechazarTrabajador(user.id, campamentoDto.id)
                    imagenResultado.setImageResource(R.drawable.trabajador_no_aceptado)
                }
                // Puedes personalizar esto según tus necesidades
            }
        }

        private class UserDiffCallback : DiffUtil.ItemCallback<UserDTO>() {
            override fun areItemsTheSame(oldItem: UserDTO, newItem: UserDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserDTO, newItem: UserDTO): Boolean {
                return oldItem.equals(newItem)
            }
        }

}