package com.example.easycamp.ui

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easycamp.R
import com.example.easycamp.domain.Models

class BuscadorAdapter : RecyclerView.Adapter<BuscadorAdapter.CampamentoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampamentoViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CampamentoViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class CampamentoViewHolder(view: View): ViewHolder(view){

        var txtNombre: TextView = view.findViewById(R.id.txtNombre)

        fun bind(item : Models.Campamento){
            txtNombre.setText(item.nombre)
        }
    }
}