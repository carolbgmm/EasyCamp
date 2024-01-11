package com.example.easycamp.util.crud;
import android.util.Log;

import com.example.easycamp.domain.UserDTO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUserManager {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("usuarios");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    // Create (Agregar)
    public void agregarUsuario(UserDTO usuario) {
        String id = database.push().getKey();
        if (id != null) {
            usuario.setId(id);
            database.child(id).setValue(usuario);
        }
    }

    // Read (Leer)
    public void obtenerUsuarios(final OnDataReceivedListener<List<UserDTO>> listener) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<UserDTO> usuarios = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    UserDTO usuario = data.getValue(UserDTO.class);
                    if (usuario != null) {
                        usuarios.add(usuario);
                    }
                }
                listener.onDataReceived(usuarios);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
            }
        });
    }

    // Update (Actualizar)
    public void actualizarUsuario(UserDTO usuario) {
        String id = usuario.getId();
        if (id != null) {
            database.child(id).setValue(usuario);
        }
    }

    // Delete (Eliminar)
    public void eliminarUsuario(UserDTO usuario) {
        String id = usuario.getId();
        if (id != null) {
            database.child(id).removeValue();
        }
    }

    // Interfaz para manejar la recepción de datos
    public interface OnDataReceivedListener<T> {
        void onDataReceived(T data);
    }

    public void obtenerUserDTOPorId(String userId, OnUserDTOReceivedListener listener) {
        database.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                UserDTO userDTO = snapshot.getValue(UserDTO.class);
                if (userDTO != null) {
                    listener.onUserDTOReceived(userDTO);
                } else {
                    Log.w("FirebaseUserManager", "No se encontró UserDTO para el ID de usuario: " + userId);
                    listener.onUserDTOReceived(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
                Log.e("FirebaseUserManager", "Error al obtener UserDTO por ID de usuario", error.toException());
                listener.onUserDTOReceived(null);
            }
        });
    }

    // Interfaz para manejar la recepción de UserDTO
    public interface OnUserDTOReceivedListener {
        void onUserDTOReceived(UserDTO userDTO);
    }

    public void obtenerUsuarioActual(OnUserDTOReceivedListener listener) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();

            // Llamar al método obtenerUserDTOPorId utilizando la ID del usuario actual
            obtenerUserDTOPorId(userId, new OnUserDTOReceivedListener() {
                @Override
                public void onUserDTOReceived(UserDTO userDTO) {
                    if (userDTO != null) {
                        // El UserDTO se ha obtenido con éxito
                        listener.onUserDTOReceived(userDTO);
                    } else {
                        // No se encontró un UserDTO para el usuario actual
                        Log.d("FirebaseUserManager", "No se encontró UserDTO para el usuario actual");
                        listener.onUserDTOReceived(null);
                    }
                }
            });
        } else {
            // El usuario actual es nulo
            Log.d("FirebaseUserManager", "El usuario actual es nulo");
            listener.onUserDTOReceived(null);
        }
    }

    public void cerrarSesion() {
        FirebaseAuth.getInstance().signOut();
    }
}
