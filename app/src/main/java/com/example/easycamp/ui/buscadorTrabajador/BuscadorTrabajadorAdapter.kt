package com.example.easycamp.ui.buscadorTrabajador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easycamp.R
import com.example.easycamp.domain.CampamentoDTO
import com.example.easycamp.domain.FavoritoDTO
import com.example.easycamp.domain.UserDTO
import com.example.easycamp.util.crud.FirebaseFavoritoManager
import com.example.easycamp.util.crud.FirebaseUserManager

class BuscadorTrabajadorAdapter (val listaCampamento: List<CampamentoDTO>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<BuscadorTrabajadorAdapter.CampamentoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampamentoViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.linea_recycler_view_buscador_trabajador
                , parent, false)
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
        fun onItemClick(item: CampamentoDTO?)
    }

    class CampamentoViewHolder(view: View): RecyclerView.ViewHolder(view) {

        var txtNombre = view.findViewById<TextView>(R.id.txtNombre)
        var txtDescripcion = view.findViewById<TextView>(R.id.txtDescripcion)
        var imgFavoritos = view.findViewById<ImageButton>(R.id.imgFavoritos)

        private val firebaseFavoritoManager = FirebaseFavoritoManager()
        private val firebaseUserManager = FirebaseUserManager()

        fun bind(item: CampamentoDTO, listener: OnItemClickListener) {
            txtNombre.setText(item.nombre)
            txtDescripcion.setText(item.descripcion)
            var userId:String =""
            // Obtener el usuario actual
            firebaseUserManager.obtenerUsuarioActual(object : FirebaseUserManager.OnUserDTOReceivedListener {
                override fun onUserDTOReceived(userDTO: UserDTO?) {
                    userDTO?.let { user ->
                         userId = user.id

                        // Obtener la lista de favoritos del usuario actual
                        firebaseFavoritoManager.obtenerFavoritosPorUsuario(userId, object : FirebaseFavoritoManager.OnFavoritosReceivedListener {
                            override fun onFavoritosReceived(favoritos: List<FavoritoDTO>) {
                                // Verificar si el campamento está en la lista de favoritos
                                val isFavorito = favoritos.any { it.campamentoId == item.id }

                                // Establecer la imagen de favorito según el resultado
                                if (isFavorito) {
                                    imgFavoritos.setImageResource(R.drawable.favoritos_relleno)
                                } else {
                                    imgFavoritos.setImageResource(R.drawable.favoritos_vacio)
                                }
                            }

                            override fun onError(exception: Exception) {
                                // Manejar errores según tus necesidades
                            }
                        })
                    }
                }
            })

            imgFavoritos?.setOnClickListener {
                // Aquí puedes mantener la lógica existente para cambiar el estado del favorito
                firebaseFavoritoManager.toggleFavorito(userId, item.id, object : FirebaseFavoritoManager.OnFavoritoToggleListener {
                    override fun onFavoritoToggled(isFavorito: Boolean) {
                        if (isFavorito) {
                            imgFavoritos.setImageResource(R.drawable.favoritos_relleno)
                        } else {
                            imgFavoritos.setImageResource(R.drawable.favoritos_vacio)
                        }
                    }
                })
            }

            // cargar imagen
            itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }

}