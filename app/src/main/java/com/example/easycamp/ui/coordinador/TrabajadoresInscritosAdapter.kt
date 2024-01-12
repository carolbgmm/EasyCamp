package com.example.easycamp.ui.coordinador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.HijoDTO
import com.example.easycamp.domain.UserDTO

class TrabajadoresInscritosAdapter : ListAdapter<UserDTO, TrabajadoresInscritosAdapter.TrabajadorViewHolder>(UserDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrabajadorViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_trabajador, parent, false)
            return TrabajadorViewHolder(view)
        }

        override fun onBindViewHolder(holder: TrabajadorViewHolder, position: Int) {
            val hijo = getItem(position)
            holder.bind(hijo)
        }

        class TrabajadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val tvNombreTrabajador: TextView = itemView.findViewById(R.id.tvNombreTrabajador)
            private val tvApellidosTrabajador: TextView = itemView.findViewById(R.id.tvApellidosTrabajador)
            private val tvEdadTrabajador: TextView = itemView.findViewById(R.id.tvEdadTrabajador)
            private val tvNombreUsuarioTrabajador: TextView = itemView.findViewById(R.id.tvNombreUsuarioTrabajador)

            fun bind(user: UserDTO) {
                tvNombreTrabajador.text = user.nombre
                tvApellidosTrabajador.text = user.apellidos
                tvEdadTrabajador.text = "Edad: ${user.edad}"
                tvNombreUsuarioTrabajador.text = user.nombreUsuario
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