package com.example.easycamp.ui.buscadorTrabajador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.TareaDTO

class TareasTrabajadorAdapter(val listaTareas: List<TareaDTO>, val listener: TareasTrabajadorAdapter.OnItemClickListener) :
    RecyclerView.Adapter<TareasTrabajadorAdapter.TareaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.linea_recycler_view_tareas_trabajador
                , parent, false)
        return TareaViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listaTareas.size
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea = listaTareas[position]
        holder.bind(tarea, listener)
    }

    fun interface OnItemClickListener {
        fun onItemClick(item: TareaDTO?)
    }

    class TareaViewHolder(view: View): RecyclerView.ViewHolder(view) {

        var txtTitulo = view.findViewById<TextView>(R.id.txtTituloTarea)
        var txtDescripcion = view.findViewById<TextView>(R.id.txtDescripcionTarea)
        var txtHora = view.findViewById<TextView>(R.id.txtHoraTarea)
        var imgTick = view.findViewById<ImageButton>(R.id.imgTick)

        fun bind(item: TareaDTO, listener: OnItemClickListener) {
            txtTitulo.setText(item.titulo)
            txtDescripcion.setText(item.descripcion)
            txtHora.setText(item.fechaLimite)

            if(item.hecha){
                imgTick.setImageResource(R.drawable.tick_relleno)
            } else {
                imgTick.setImageResource(R.drawable.tick_vacio)
            }

            imgTick?.setOnClickListener {
                if(item.hecha){
                    imgTick.setImageResource(R.drawable.tick_vacio)
                    item.hecha = false
                } else {
                    imgTick.setImageResource(R.drawable.tick_relleno)
                    item.hecha = true
                }
            }
            // cargar imagen
            itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }


}