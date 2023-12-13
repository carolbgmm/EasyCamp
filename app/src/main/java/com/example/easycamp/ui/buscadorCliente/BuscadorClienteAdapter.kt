package com.example.easycamp.ui.buscadorCliente

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDto
import com.example.easycamp.domain.LoggedUserDTO
import com.example.easycamp.util.DBHelper


class BuscadorClienteAdapter(val listaCampamento: List<CampamentoDto>, val listener: OnItemClickListener) :
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
        val campamento = listaCampamento[position]
        holder.bind(campamento, listener)
    }

    fun interface OnItemClickListener {
        fun onItemClick(item: CampamentoDto?)
    }

    class CampamentoViewHolder(view: View): ViewHolder(view){
        val bdHelper =  DBHelper.getInstance(null)
        val userId = LoggedUserDTO.getInstance(null).user.id

        var txtNombre = view.findViewById<TextView>(R.id.txtNombre)
        var txtDescripcion = view.findViewById<TextView>(R.id.txtDescripcion)
        var imgFavoritos = view.findViewById<ImageButton>(R.id.imgFavoritos)

        fun bind(item : CampamentoDto, listener: OnItemClickListener){
            txtNombre.setText(item.nombre)
            txtDescripcion.setText(item.descripcion)

            if(item.isFavorito){
                imgFavoritos.setImageResource(R.drawable.favoritos_relleno)
            } else {
                imgFavoritos.setImageResource(R.drawable.favoritos_vacio)
            }


            imgFavoritos?.setOnClickListener {
                if(item.isFavorito){
                    imgFavoritos.setImageResource(R.drawable.favoritos_vacio)
                    bdHelper.quitarDeFavoritos(userId, item.id)
                    item.isFavorito=false
                } else {
                    imgFavoritos.setImageResource(R.drawable.favoritos_relleno)
                    bdHelper.agregarFavorito(userId, item.id)
                    item.isFavorito=true
                }
            }
            // cargar imagen
            itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }
}
