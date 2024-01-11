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
import com.example.easycamp.domain.CampamentoDTO
import com.example.easycamp.domain.HijoDTO
import com.example.easycamp.util.crud.FirebaseInscritosManager
import com.google.firebase.auth.FirebaseAuth

class ListaApuntarHijosAdapter(val campamento: CampamentoDTO) :
    ListAdapter<HijoDTO, ListaApuntarHijosAdapter.HijoViewHolder>(HijoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HijoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_apuntar_hijo, parent, false)
        return HijoViewHolder(view)
    }

    override fun onBindViewHolder(holder: HijoViewHolder, position: Int) {
        val hijo = getItem(position)
        holder.bind(hijo, campamento)
    }

    class HijoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvNombreHijo: TextView = itemView.findViewById(R.id.tvNombreHijo)
        private val tvApellidos: TextView = itemView.findViewById(R.id.tvApellidos)
        private val tvEdad: TextView = itemView.findViewById(R.id.tvEdad)
        private val tvObservaciones: TextView = itemView.findViewById(R.id.tvObservaciones)
        private val btnAdd: ImageButton = itemView.findViewById(R.id.btnAdd)

        fun bind(hijo: HijoDTO, campamento: CampamentoDTO) {
            val firebaseInscritosManager = FirebaseInscritosManager()



            val hijoId=hijo.id
           val  campamentoId=campamento.id
            val  padreId=FirebaseAuth.getInstance().currentUser?.uid
            tvNombreHijo.text = hijo.nombre
            tvApellidos.text = hijo.apellidos
            tvEdad.text = "Edad: ${hijo.edad}"
            tvObservaciones.text = "Observaciones: ${hijo.observaciones}"

            btnAdd.setOnClickListener {
                // Inscribir el hijo al campamento utilizando FirebaseInscritosManager



                firebaseInscritosManager.inscribirHijoAlCampamento(
                    hijoId,
                    campamentoId,
                    padreId,
                    object : FirebaseInscritosManager.OnInscripcionCompletadaListener {
                        override fun onInscripcionCompletada() {
                            btnAdd.setImageResource(R.drawable.icon_hijo_apuntado)
                        }
                    }
                )

            }
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
