package com.example.easycamp.util.crud;
import com.example.easycamp.domain.TareaDTO;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class FirebaseTareaManager {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("tareas");

    // Create (Agregar)
    public void agregarTarea(TareaDTO tarea) {
        String id = database.push().getKey();
        if (id != null) {
            tarea.setId(id);
            database.child(id).setValue(tarea);
        }
    }

    // Read (Leer)
    public void obtenerTareas(final OnDataReceivedListener<List<TareaDTO>> listener) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<TareaDTO> tareas = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    TareaDTO tarea = data.getValue(TareaDTO.class);
                    if (tarea != null) {
                        tareas.add(tarea);
                    }
                }
                listener.onDataReceived(tareas);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
            }
        });
    }

    // Update (Actualizar)
    public void actualizarTarea(TareaDTO tarea) {
        String id = tarea.getId();
        if (id != null) {
            database.child(id).setValue(tarea);
        }
    }

    // Delete (Eliminar)
    public void eliminarTarea(TareaDTO tarea) {
        String id = tarea.getId();
        if (id != null) {
            database.child(id).removeValue();
        }
    }

    // Interfaz para manejar la recepción de datos
    public interface OnDataReceivedListener<T> {
        void onDataReceived(T data);
    }

    public void obtenerTareasPorUsuario(String idUsuario, OnTareasReceivedListener listener) {
        Query query = database.orderByChild("idUsuario").equalTo(idUsuario);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<TareaDTO> tareas = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    TareaDTO tarea = data.getValue(TareaDTO.class);
                    if (tarea != null) {
                        tareas.add(tarea);
                    }
                }
                listener.onTareasReceived(tareas);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
                listener.onError(error.toException());
            }
        });
    }

    public interface OnTareasReceivedListener {
        void onTareasReceived(List<TareaDTO> tareas);
        void onError(Exception exception);
    }
}
