package com.example.easycamp.ui.buscadorCliente

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easycamp.R
import com.example.easycamp.domain.Campamento
import org.w3c.dom.Text

class BuscadorClienteAdapter( val listaCampamento: List<Campamento>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<BuscadorClienteAdapter.CampamentoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampamentoViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.linea_recycler_view_buscador_cliente, parent, false)
        return CampamentoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listaCampamento.size
    }

    override fun onBindViewHolder(holder: CampamentoViewHolder, position: Int) {
        val campamento = listaCampamento.get(position)
        holder.bind(campamento, listener)
    }

    interface OnItemClickListener {
        fun onItemClick(item: Campamento?)
    }

    class CampamentoViewHolder(view: View): ViewHolder(view){

        var txtNombre = view.findViewById<TextView>(R.id.txtNombre)
        var txtDescripcion = view.findViewById<TextView>(R.id.txtDescripcion)

        fun bind(item : Campamento, listener: OnItemClickListener){
            txtNombre.setText(item.nombre)
            txtDescripcion.setText(item.descripcion)

            // cargar imagen
            itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }
}