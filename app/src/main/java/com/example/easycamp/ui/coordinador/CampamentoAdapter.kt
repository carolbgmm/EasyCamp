package com.example.easycamp.ui.coordinador



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto

class CampamentosAdapter(private val campamentos: List<CampamentoDto>, private val listener: OnItemClickListener) : RecyclerView.Adapter<CampamentosAdapter.CampamentoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampamentoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_campamento, parent, false)
        return CampamentoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CampamentoViewHolder, position: Int) {
        val campamento = campamentos[position]
        holder.bind(campamento, listener)
    }

    override fun getItemCount(): Int {
        return campamentos.size
    }

    interface OnItemClickListener {
        fun onItemClick(campamento: CampamentoDto)
    }

    class CampamentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)
        private val ubicacionTextView: TextView = itemView.findViewById(R.id.ubicacionTextView)

        fun bind(campamento: CampamentoDto, listener: OnItemClickListener) {
            nombreTextView.text = campamento.nombre
            ubicacionTextView.text = campamento.ubicacion

            itemView.setOnClickListener {
                listener.onItemClick(campamento)
            }
        }
    }
}
