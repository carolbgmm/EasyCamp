package com.example.easycamp.util.crud;
import com.example.easycamp.domain.FavoritoDTO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;
public class FirebaseFavoritoManager {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("favoritos");

    // Create (Agregar)
    public void agregarFavorito(FavoritoDTO favorito) {
        String id = database.push().getKey();
        if (id != null) {
            favorito.setId(id);
            database.child(id).setValue(favorito);
        }
    }

    // Read (Leer)
    public void obtenerFavoritos(final OnDataReceivedListener<List<FavoritoDTO>> listener) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<FavoritoDTO> favoritos = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    FavoritoDTO favorito = data.getValue(FavoritoDTO.class);
                    if (favorito != null) {
                        favoritos.add(favorito);
                    }
                }
                listener.onDataReceived(favoritos);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
            }
        });
    }

    // Update (Actualizar)
    public void actualizarFavorito(FavoritoDTO favorito) {
        String id = favorito.getId();
        if (id != null) {
            database.child(id).setValue(favorito);
        }
    }

    // Delete (Eliminar)
    public void eliminarFavorito(FavoritoDTO favorito) {
        String id = favorito.getId();
        if (id != null) {
            database.child(id).removeValue();
        }
    }

    // Interfaz para manejar la recepción de datos
    public interface OnDataReceivedListener<T> {
        void onDataReceived(T data);
    }

    public void toggleFavorito(String userId, String campamentoId, OnFavoritoToggleListener listener) {
        // Verificar si el campamento ya está marcado como favorito
        database.child(userId).child(campamentoId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // El campamento ya está marcado como favorito, quitarlo de favoritos
                    database.child(userId).child(campamentoId).removeValue();
                    listener.onFavoritoToggled(false);
                } else {
                    // El campamento no está marcado como favorito, agregarlo a favoritos
                    String favoritoId = database.push().getKey();
                    FavoritoDTO favorito = new FavoritoDTO(favoritoId, userId, campamentoId);
                    database.child(userId).child(campamentoId).setValue(favorito);
                    listener.onFavoritoToggled(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejar errores según tus necesidades
            }
        });
    }

    // Interfaz para manejar el cambio de estado de favorito
    public interface OnFavoritoToggleListener {
        void onFavoritoToggled(boolean isFavorito);
    }
    public void obtenerFavoritosPorUsuario(String idUsuario, OnFavoritosReceivedListener listener) {
        Query query = database.orderByChild("usuarioId").equalTo(idUsuario);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<FavoritoDTO> favoritos = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    FavoritoDTO favorito = data.getValue(FavoritoDTO.class);
                    if (favorito != null) {
                        favoritos.add(favorito);
                    }
                }
                listener.onFavoritosReceived(favoritos);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
                listener.onError(error.toException());
            }
        });
    }

    public interface OnFavoritosReceivedListener {
        void onFavoritosReceived(List<FavoritoDTO> favoritos);
        void onError(Exception exception);
    }

    public void obtenerFavoritosDelUsuarioActual(OnFavoritosReceivedListener listener) {
        // Obtener el usuario actual desde Firebase Authentication
        FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioActual != null) {
            // Utilizar el ID del usuario actual para obtener sus favoritos
            obtenerFavoritosPorUsuario(usuarioActual.getUid(), listener);
        } else {
            // Manejar el caso en el que no se haya iniciado sesión
            listener.onError(new Exception("No se ha iniciado sesión"));
        }
    }

}