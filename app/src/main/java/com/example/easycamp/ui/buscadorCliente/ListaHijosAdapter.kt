package com.example.easycamp.ui.buscadorCliente

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.HijoDTO
class ListaHijosAdapter : ListAdapter<HijoDTO, ListaHijosAdapter.HijoViewHolder>(HijoDiffCallback()) {

    private var onDeleteClickListener: ((Int) -> Unit)? = null

    fun setOnDeleteClickListener(listener: (Int) -> Unit) {
        onDeleteClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HijoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_hijo, parent, false)
        return HijoViewHolder(view, onDeleteClickListener)
    }

    override fun onBindViewHolder(holder: HijoViewHolder, position: Int) {
        val hijo = getItem(position)
        holder.bind(hijo)
    }

    class HijoViewHolder(itemView: View, onDeleteClickListener: ((Int) -> Unit)?) : RecyclerView.ViewHolder(itemView) {

        private val tvNombreHijo: TextView = itemView.findViewById(R.id.tvNombreHijo)
        private val tvApellidos: TextView = itemView.findViewById(R.id.tvApellidos)
        private val tvEdad: TextView = itemView.findViewById(R.id.tvEdad)
        private val tvObservaciones: TextView = itemView.findViewById(R.id.tvObservaciones)
        private val btnEliminar: ImageButton = itemView.findViewById(R.id.btnEliminarHijo)

        init {
            btnEliminar.setOnClickListener {
                onDeleteClickListener?.invoke(bindingAdapterPosition)
            }
        }

        fun bind(hijo: HijoDTO) {
            tvNombreHijo.text = hijo.nombre
            tvApellidos.text = hijo.apellidos
            tvEdad.text = "Edad: ${hijo.edad}"
            tvObservaciones.text = "Observaciones: ${hijo.observaciones}"
        }
    }

    private class HijoDiffCallback : DiffUtil.ItemCallback<HijoDTO>() {
        override fun areItemsTheSame(oldItem: HijoDTO, newItem: HijoDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HijoDTO, newItem: HijoDTO): Boolean {
            return oldItem.equals(newItem)
        }
    }
}


