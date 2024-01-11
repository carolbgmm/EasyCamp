package com.example.easycamp.util.crud;
import com.example.easycamp.domain.HijoDTO;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHijoManager {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("hijos");

    // Create (Agregar)
    public HijoDTO agregarHijo(HijoDTO hijo) {
        String id = database.push().getKey();
        if (id != null) {
            hijo.setId(id);
            database.child(id).setValue(hijo);

        }
        return hijo;
    }

    // Read (Leer)
    public void obtenerHijos(final OnDataReceivedListener<List<HijoDTO>> listener) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<HijoDTO> hijos = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    HijoDTO hijo = data.getValue(HijoDTO.class);
                    if (hijo != null) {
                        hijos.add(hijo);
                    }
                }
                listener.onDataReceived(hijos);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
            }
        });
    }

    // Update (Actualizar)
    public void actualizarHijo(HijoDTO hijo) {
        String id = hijo.getId();
        if (id != null) {
            database.child(id).setValue(hijo);
        }
    }

    // Delete (Eliminar)
    public void eliminarHijo(HijoDTO hijo) {
        String id = hijo.getId();
        if (id != null) {
            database.child(id).removeValue();
        }
    }

    // Interfaz para manejar la recepción de datos
    public interface OnDataReceivedListener<T> {
        void onDataReceived(T data);
    }

    public void obtenerHijosPorIdPadre(String idPadre, OnHijosReceivedListener listener) {
        Query query = database.orderByChild("id_padre").equalTo(idPadre);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<HijoDTO> hijos = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    HijoDTO hijo = data.getValue(HijoDTO.class);
                    if (hijo != null) {
                        hijos.add(hijo);
                    }
                }
                listener.onHijosReceived(hijos);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
            }
        });
    }

    public interface OnHijosReceivedListener {
        void onHijosReceived(List<HijoDTO> hijos);

        void onError(DatabaseException toException);
    }

    public void obtenerHijosPorUsuario(String idPadre, OnHijosReceivedListener listener) {
        Query query = database.orderByChild("id_padre").equalTo(idPadre);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<HijoDTO> hijos = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    HijoDTO hijo = data.getValue(HijoDTO.class);
                    if (hijo != null) {
                        hijos.add(hijo);
                    }
                }
                listener.onHijosReceived(hijos);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
                listener.onError(error.toException());
            }
        });
    }

}
