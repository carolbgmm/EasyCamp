package com.example.easycamp.ui.buscadorCliente

// ListaHijosAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.HijoDTO

class ListaHijosAdapter : ListAdapter<HijoDTO, ListaHijosAdapter.HijoViewHolder>(HijoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HijoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_hijo, parent, false)
        return HijoViewHolder(view)
    }

    override fun onBindViewHolder(holder: HijoViewHolder, position: Int) {
        val hijo = getItem(position)
        holder.bind(hijo)
    }

    class HijoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvNombreHijo: TextView = itemView.findViewById(R.id.tvNombreHijo)
        private val tvApellidos: TextView = itemView.findViewById(R.id.tvApellidos)
        private val tvEdad: TextView = itemView.findViewById(R.id.tvEdad)
        private val tvObservaciones: TextView = itemView.findViewById(R.id.tvObservaciones)

        fun bind(hijo: HijoDTO) {
            tvNombreHijo.text = hijo.nombre
            tvApellidos.text = hijo.apellidos
            tvEdad.text = "Edad: ${hijo.edad}"
            tvObservaciones.text = "Observaciones: ${hijo.observaciones}"
            // Puedes personalizar esto según tus necesidades
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
